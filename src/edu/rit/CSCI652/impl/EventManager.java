package edu.rit.CSCI652.impl;


import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Message;
import edu.rit.CSCI652.demo.Topic;

import java.io.IOException;
import java.util.ArrayList;

public class EventManager{
	
	/*
	 * Start the repo service
	 */
	private void startService() {

		UDPSystem udpSystem= new UDPSystem(6789);
		udpSystem.getMessages(new ServerI() {

			@Override
			public void success(Message recvdMessage, String ip, int port) {

				System.out.println("Sub port:" + port + ", type:" + recvdMessage.getType());

				Message sendMessage = null;

				switch (recvdMessage.getType()){

					case Message.PUBLISH_REQUEST_TOPICS:

						sendMessage = new Message();
						sendMessage.setType(Message.PUBLISH_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getAllTopics());


						try {

							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case Message.PUBLISH_SEND_EVENT:

						DbConnection conn = DbConnection.getInstance();
						Event event = recvdMessage.getEvent();

						conn.insertEvent(event.getTopicId(), event.getTitle(), event.getContent());

						ArrayList<Event> events = conn.getAllEvents();

						for(Event ev:events)
							System.out.println(ev);

						break;

					case Message.SUBSCRIBE_SELECTED_TOPIC:

						Topic topic = recvdMessage.getTopic();

						// TODO
						DbConnection.getInstance().insertSubscriber(Integer.toString(port), 0);
						DbConnection.getInstance().insertSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(Integer.toString(port)),
								topic.getId());

						System.out.println(port + " has subscribed to " + topic.getName());

						break;



					case Message.SUBSCRIBE_REQUEST_TOPICS:
						DbConnection con = DbConnection.getInstance();
						sendMessage = new Message();
						sendMessage.setType(Message.SUBSCRIBE_REQUEST_TOPICS);

						//TODO
						sendMessage.setTopicList(con.getSubscriberTopics(con.getSubscriberId(String.valueOf(port)), false));

						try {

							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;

					case Message.READ_REQUEST_EVENTS:


						// TOD
						ArrayList<Event> eventsArrayList = DbConnection.getInstance().getAllEventsForSubscriber(Integer.toString(port));

						sendMessage = new Message();
						sendMessage.setType(Message.READ_REQUEST_EVENTS);
						sendMessage.setEventList(eventsArrayList);

						try {
							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}
						DbConnection.getInstance().updateSubscriberLastActive(Integer.toString(port));

						break;

					case Message.UNSUB_SELECT_TOPIC:


						// TODO
						DbConnection.getInstance().removeSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(Integer.toString(port)),
								recvdMessage.getTopic().getId() );

						System.out.println(port + " has unsubbed from " + recvdMessage.getTopic().getName());


						break;

					case Message.UNSUB_REQUEST_TOPICS:

						//TODO
						sendMessage = new Message();
						sendMessage.setType(Message.UNSUB_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getSubscriberTopics(DbConnection.getInstance().getSubscriberId(String.valueOf(port)), true));

						try {
							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}



						break;


					case Message.ADVERTISE_REQUEST_TOPICS:

						DbConnection.getInstance().insertTopic(recvdMessage.getTopic().getName(), recvdMessage.getTopic().getKeywords());
						sendMessage = new Message();
						sendMessage.setType(Message.ADVERTISE_REQUEST_TOPICS);
						try {

							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(port + " has added topic:" + recvdMessage.getTopic().getName());


						break;
				}
			}
		});
	}

	public static void main(String[] args)
	{
		new EventManager().startService();
	}


}
