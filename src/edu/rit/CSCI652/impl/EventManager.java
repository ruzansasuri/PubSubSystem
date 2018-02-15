package edu.rit.CSCI652.impl;


import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Message;
import edu.rit.CSCI652.demo.Topic;

import java.io.IOException;
import java.util.ArrayList;

public class EventManager{

	public static final int SEND_PORT = 6790;
	public static final int RECEIVE_PORT = 6789;
	/**
	 * @author Thomas Binu
	 * @author Ruzan Sasuri
	 * @author Amol Gaikwad
	 *
	 * Event manager class to handle subscribers and publishers
	 */

	private void startService() {

		TCPSystem tcpSystem = new TCPSystem(SEND_PORT, RECEIVE_PORT);

		tcpSystem.setTCPInterface(new ServerI() {

			@Override
			public void gotMessage(Message recvdMessage, String ip) {

				Logging.print("Sub PORT:" + SEND_PORT + ", type:" + recvdMessage.getType());

				Message sendMessage = null;

				switch (recvdMessage.getType()){

					case Message.PUBLISH_REQUEST_TOPICS:

						sendMessage = new Message();
						sendMessage.setType(Message.PUBLISH_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getAllTopics());


						try {

							tcpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(ip + " publish:requested topic");

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
						System.out.println(ip + " publish:send event");

						break;

					case Message.SUBSCRIBE_SELECTED_TOPIC:

						Topic topic = recvdMessage.getTopic();

						DbConnection.getInstance().insertSubscriber(ip);
						DbConnection.getInstance().insertSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(ip),
								topic.getId());

						System.out.println(ip + " subscribe:selected " + topic.getName());

						break;



					case Message.SUBSCRIBE_REQUEST_TOPICS:
						DbConnection con = DbConnection.getInstance();
						sendMessage = new Message();
						sendMessage.setType(Message.SUBSCRIBE_REQUEST_TOPICS);

						sendMessage.setTopicList(con.getSubscriberTopics(con.getSubscriberId(ip), false));

						try {


							tcpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(ip + " subscribe:requested topics");

						break;

					case Message.READ_REQUEST_EVENTS:


						// TOD
						ArrayList<Event> eventsArrayList = DbConnection.getInstance().getAllEventsForSubscriber(ip);

						sendMessage = new Message();
						sendMessage.setType(Message.READ_REQUEST_EVENTS);
						sendMessage.setEventList(eventsArrayList);

						try {

							tcpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}
						DbConnection.getInstance().updateSubscriberLastActive(ip);

						System.out.println(ip + " read events");

						break;

					case Message.READ_FROMKEYWORD_REQUEST_EVENTS:
						Topic recvdTopic = recvdMessage.getTopic();

						// TOD
						ArrayList<Event> eventsList = DbConnection.getInstance().getAllEventsFromKeyword(ip,recvdTopic.getKeywords());

						sendMessage = new Message();
						sendMessage.setType(Message.READ_FROMKEYWORD_REQUEST_EVENTS);
						sendMessage.setEventList(eventsList);

						try {

							tcpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}
						DbConnection.getInstance().updateSubscriberLastActive(ip);

						System.out.println(ip + " read events using keywords");

						break;

					case Message.UNSUB_SELECT_TOPIC:


						DbConnection.getInstance().removeSubscriberTopic(
								DbConnection.getInstance().getSubscriberId(ip),
								recvdMessage.getTopic().getId() );

						System.out.println(ip + " has unsubbed from " + recvdMessage.getTopic().getName());
						break;

					case Message.UNSUB_REQUEST_TOPICS:


						sendMessage = new Message();
						sendMessage.setType(Message.UNSUB_REQUEST_TOPICS);
						sendMessage.setTopicList(DbConnection.getInstance().getSubscriberTopics(DbConnection.getInstance().getSubscriberId(ip), true));

						try {

							tcpSystem.sendMessage(sendMessage, ip);

						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(ip + " unsub: requested topic");

						break;


					case Message.ADVERTISE_REQUEST_TOPICS:

						DbConnection.getInstance().insertTopic(recvdMessage.getTopic().getName(), recvdMessage.getTopic().getKeywords());
						sendMessage = new Message();
						sendMessage.setType(Message.ADVERTISE_REQUEST_TOPICS);
						try 
						{

							tcpSystem.sendMessage(sendMessage, ip);

						} 
						catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(ip + " advertise: requested topic");
						break;
				}
			}

			@Override
			public void failure() {

			}

			@Override
			public void sentMessageSuccess() {

			}

			@Override
			public void sendMessageFailed() {

			}
		});


		tcpSystem.startMessageServer();
	}

	public static void main(String[] args)
	{
		new EventManager().startService();
	}


}
