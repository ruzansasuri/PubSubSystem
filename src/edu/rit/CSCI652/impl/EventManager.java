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

						conn.insertEvent(event.getTopicId(), event.getTitle(), event.getContent(), event.getPublishDateTime());

						ArrayList<Event> events = conn.getAllEvents();

						for(Event ev:events)
							System.out.println(ev);

						break;

					case Message.SUBSCRIBE_SELECTED_TOPIC:

						Topic topic = recvdMessage.getTopic();

						// TODO
						DbConnection.getInstance().insertSubscriber(Integer.toString(port), System.currentTimeMillis());
						DbConnection.getInstance().insertSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(Integer.toString(port)),
								topic.getId());

						break;



					case Message.SUBSCRIBE_REQUEST_TOPICS:

						sendMessage = new Message();
						sendMessage.setType(Message.SUBSCRIBE_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getSubscriberTopics(port, true));

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


						break;

					case Message.UNSUBE_SELECT_TOPIC:


						// TODO
						DbConnection.getInstance().removeSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(Integer.toString(port)),
								recvdMessage.getTopic().getId() );


						break;

					case Message.UNSUB_REQUEST_TOPICS:

						//TODO
						sendMessage = new Message();
						sendMessage.setType(Message.UNSUB_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getSubscriberTopics(port, true));

						try {
							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;


					case Message.ADVERTISE_REQUEST_TOPICS:

						sendMessage = new Message();
						sendMessage.setType(Message.ADVERTISE_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getAllTopics());

						try {

							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}


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
