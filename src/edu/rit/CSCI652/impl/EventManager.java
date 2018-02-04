package edu.rit.CSCI652.impl;


import com.google.gson.Gson;
import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Message;
import edu.rit.CSCI652.demo.PubSubMenu;
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
			public void success(Message message, int port) {

				System.out.println("Sub port:" + port + ", type:" + message.getType());

				switch (message.getType()){

					case Message.REQUEST_TOPICS:

						Message sendMessage = new Message();
						sendMessage.setType(Message.REQUEST_TOPICS);
						ArrayList<Topic> topicArrayList = DbConnection.getInstance().getAllTopics();
						sendMessage.setTopicList(topicArrayList);


						try {
							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case Message.SEND_EVENT:

						DbConnection conn = DbConnection.getInstance();
						Event event = message.getEvent();
						int time = (int)(event.getPublishDateTime() / 1000L);
						conn.insertEvent(event.getTopicId(), event.getTitle(), event.getContent(), time);

						ArrayList<Event> events = conn.getAllEvents();

						for(Event ev:events)
							System.out.println(ev);

						break;
					case 3:
						break;
				}
			}

			@Override
			public void success(Message message, String ip) {

			}
		});
	}

	public static void main(String[] args)
	{
		new EventManager().startService();
	}


}
