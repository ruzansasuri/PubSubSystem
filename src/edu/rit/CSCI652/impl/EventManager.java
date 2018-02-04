package edu.rit.CSCI652.impl;


import com.google.gson.Gson;
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

				System.out.println("Sub port:" + port);

				switch (message.getType()){

					case 1:

						Message sendMessage = new Message();
						ArrayList<Topic> topicArrayList = DbConnection.getInstance().getAllTopics();
						sendMessage.setTopicList(topicArrayList);


						try {
							// TODO
							udpSystem.sendMessageLocal(sendMessage, port);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case 2:
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
