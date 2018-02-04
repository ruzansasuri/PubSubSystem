package edu.rit.CSCI652.impl;
import edu.rit.CSCI652.demo.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PubSubAgent implements PublisherI, SubscriberI
{

	public static final String SERVER_IP = "6789";


	public static void main(String[] args){


		int port = 9999; //DEFAULT PORT
		if(args.length == 1){
			port = Integer.parseInt(args[0]);
		}

		UDPSystem udpSystem = new UDPSystem(port);
		udpSystem.getMessages(new ServerI() {

			@Override
			public void success(Message message, String ip, int port) {

				System.out.println("Server port:" + port + ", Message Type:" + message.getType());

				switch (message.getType()){

					case Message.PUBLISH_REQUEST_TOPICS:

						ArrayList<Topic> topicArrayList = message.getTopicList();

						new PubSubMenu().showTopics(topicArrayList, new PubSubMenu.topicInterface() {
							@Override
							public void selectedTopic(Topic topic, String title, String content) {

								Event event = new Event(topic.getId(), title, content, System.currentTimeMillis());
								Message sendMessage = new Message();
								sendMessage.setType(Message.PUBLISH_SEND_EVENT);
								sendMessage.setEvent(event);
								try {

									//TODO
									udpSystem.sendMessageLocal(sendMessage, port);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});

						break;
					case Message.SUBSCRIBE_REQUEST_TOPICS:


						new PubSubMenu().showTopics(message.getTopicList(), new PubSubMenu.topicInterface() {
							@Override
							public void selectedTopic(Topic topic, String title, String content) {

								Message sendMessage = new Message();
								sendMessage.setType(Message.SUBSCRIBE_SELECTED_TOPIC);
								sendMessage.setTopic(topic);
								try {

									//TODO
									udpSystem.sendMessageLocal(sendMessage, port);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});


						break;
					case 4:
						break;
				}

			}
		});

		PubSubMenu pubSubMenu = new PubSubMenu();
		pubSubMenu.showMenu(new PubSubMenu.PubSubMenuInterface() {

			@Override
			public void invokePublish() {

				Message message = new Message();
				message.setType(Message.PUBLISH_REQUEST_TOPICS);

				try {

					//TODO
					udpSystem.sendMessageLocal(message, Integer.parseInt(SERVER_IP));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void invokeAdvertise() {

			}

			@Override
			public void invokeSubscribe() {

				Message message = new Message();
				message.setType(Message.SUBSCRIBE_REQUEST_TOPICS);

				try {

					//TODO
					udpSystem.sendMessageLocal(message, Integer.parseInt(SERVER_IP));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void invokeRead() {

			}

			@Override
			public void invokeUnsubscribe() {



			}
		});
	}

	@Override
	public void displayTopics(List<Topic> topics)
	{
		//please change the following.

	}

	@Override
	public void displayEvents(List<Event> events)
	{

	}

	@Override
	public void subscribe(Topic topic)
	{

	}

	@Override
	public void subscribe(String keyword)
	{
	}

	@Override
	public void unsubscribe(Topic topic)
	{

	}

	@Override
	public void unsubscribe()
	{


	}

	@Override
	public void listSubscribedTopics()
	{

	}

	@Override
	public void publish(Event event)
	{

	}

	@Override
	public void advertise(Topic newTopic)
	{


	}

}
