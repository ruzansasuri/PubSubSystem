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

				Logging.print("Sub port:" + port + ", type:" + recvdMessage.getType());

				Message sendMessage = null;

				switch (recvdMessage.getType()){

					case Message.PUBLISH_REQUEST_TOPICS:

						sendMessage = new Message();
						sendMessage.setType(Message.PUBLISH_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getAllTopics());


						try {

							// TODO
							udpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case Message.PUBLISH_SEND_EVENT:

						DbConnection conn = DbConnection.getInstance();
						Event event = recvdMessage.getEvent();

						conn.insertEvent(event.getTopicId(), event.getTitle(), event.getContent());
						/*
						ArrayList<Event> events = conn.getAllEvents();

						for(Event ev:events)
							System.out.println(ev);
						*/
						System.out.println("The content has been published");

						break;

					case Message.SUBSCRIBE_SELECTED_TOPIC:

						Topic topic = recvdMessage.getTopic();

						// TODO
						DbConnection.getInstance().insertSubscriber(ip, 0);
						DbConnection.getInstance().insertSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(ip),
								topic.getId());

						System.out.println(port + " has subscribed to " + topic.getName());

						break;



					case Message.SUBSCRIBE_REQUEST_TOPICS:
						DbConnection con = DbConnection.getInstance();
						sendMessage = new Message();
						sendMessage.setType(Message.SUBSCRIBE_REQUEST_TOPICS);

						//TODO
						sendMessage.setTopicList(con.getSubscriberTopics(con.getSubscriberId(ip), false));

						try {

							// TODO
							udpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;

					case Message.READ_REQUEST_EVENTS:


						// TOD
						ArrayList<Event> eventsArrayList = DbConnection.getInstance().getAllEventsForSubscriber(ip);

						sendMessage = new Message();
						sendMessage.setType(Message.READ_REQUEST_EVENTS);
						sendMessage.setEventList(eventsArrayList);

						try {
							// TODO
							udpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}
						DbConnection.getInstance().updateSubscriberLastActive(ip);

						break;

					case Message.READ_FROMKEYWORD_REQUEST_EVENTS:
						Topic recvdTopic = recvdMessage.getTopic();

						// TOD
						ArrayList<Event> eventsList = DbConnection.getInstance().getAllEventsFromKeyword(ip,recvdTopic.getKeywords());

						sendMessage = new Message();
						sendMessage.setType(Message.READ_FROMKEYWORD_REQUEST_EVENTS);
						sendMessage.setEventList(eventsList);

						try {
							// TODO
							udpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}
						DbConnection.getInstance().updateSubscriberLastActive(ip);

						break;

					case Message.UNSUB_SELECT_TOPIC:


						// TODO
						DbConnection.getInstance().removeSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(ip),
								recvdMessage.getTopic().getId() );

						System.out.println(port + " has unsubscribed from " + recvdMessage.getTopic().getName());


						break;

					case Message.UNSUB_REQUEST_TOPICS:

						//TODO
						sendMessage = new Message();
						sendMessage.setType(Message.UNSUB_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getSubscriberTopics(DbConnection.getInstance().getSubscriberId(ip), true));

						try {
							// TODO
							udpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}



						break;


					case Message.ADVERTISE_REQUEST_TOPICS:

						DbConnection.getInstance().insertTopic(recvdMessage.getTopic().getName(), recvdMessage.getTopic().getKeywords());
						sendMessage = new Message();
						sendMessage.setType(Message.ADVERTISE_REQUEST_TOPICS);
						try 
						{

							// TODO
							udpSystem.sendMessage(sendMessage, ip);

						} 
						catch (IOException e) {
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
