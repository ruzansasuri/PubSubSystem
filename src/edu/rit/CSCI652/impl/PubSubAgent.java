package edu.rit.CSCI652.impl;

import edu.rit.CSCI652.demo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Thomas Binu
 * @author Ruzan Sasuri
 * @author Amol Gaikwad
 *
 * PubSubAgent plays the role of publisher and subscriber
 */

public class PubSubAgent {

    public static final String SERVER_IP = "172.17.0.2";
    public static final int SERVER_PORT = 6789;

    public static void main(String[] args) {
        int clientPort = 9999; //DEFAULT PORT


        PubSubMenu pubSubMenu = new PubSubMenu();

        UDPSystem udpSystem = new UDPSystem(clientPort);
        udpSystem.getMessages(new ServerI() {
            @Override
            public void success(Message message, String ip, int port) {
                Logging.print("Server port:" + port + ", Message Type:" + message.getType());

                switch (message.getType()) {

                    case Message.PUBLISH_REQUEST_TOPICS:

                        if(message.getTopicList().size()==0){
                            System.out.println("There are no topics in the server");
                        }else {

                            new PubSubMenu().showTopics(message.getTopicList(), new PubSubMenu.topicInterface() {
                                @Override
                                public void selectedTopic(Topic topic) {
                                    Scanner in = new Scanner(System.in);
                                    System.out.print("Enter your title for the content:");
                                    String title = in.next();
                                    System.out.print("Enter your content:");
                                    String content = in.next();

                                    Event event = new Event(topic.getId(), title, content);
                                    Message sendMessage = new Message();
                                    sendMessage.setType(Message.PUBLISH_SEND_EVENT);
                                    sendMessage.setEvent(event);
                                    try {
                                        //TODO
                                        udpSystem.sendMessage(sendMessage, ip, port);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        pubSubMenu.showMenu();
                        break;

                    case Message.SUBSCRIBE_REQUEST_TOPICS:

                        if(message.getTopicList().size()==0){
                            System.out.println("There are no topics in the server");
                        }else {

                            new PubSubMenu().showTopics(message.getTopicList(), new PubSubMenu.topicInterface() {
                                @Override
                                public void selectedTopic(Topic topic) {
                                    Message sendMessage = new Message();
                                    sendMessage.setType(Message.SUBSCRIBE_SELECTED_TOPIC);
                                    sendMessage.setTopic(topic);
                                    try {
                                        //TODO
                                        udpSystem.sendMessage(sendMessage, ip, port);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        pubSubMenu.showMenu();
                        break;

                    case Message.UNSUB_REQUEST_TOPICS:

                        if(message.getTopicList().size()==0){
                            System.out.println("There are no topics in the server");
                        }else {
                            new PubSubMenu().showTopics(message.getTopicList(), new PubSubMenu.topicInterface() {
                                @Override
                                public void selectedTopic(Topic topic) {

                                    Message sendMessage = new Message();
                                    sendMessage.setType(Message.UNSUB_SELECT_TOPIC);
                                    sendMessage.setTopic(topic);
                                    try {

                                        //TODO
                                        udpSystem.sendMessage(sendMessage, ip, port);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }
                        pubSubMenu.showMenu();
                        break;


                    case Message.READ_REQUEST_EVENTS:

                        ArrayList<Event> eventList = message.getEventList();
                        if(eventList.size()==0){
                           System.out.println("There are no events to display");
                        }else {

                            for (Event event : eventList) {
                                System.out.println("Title: " + event.getTitle());
                                System.out.println("Content: " + event.getContent());
                                System.out.println();
                            }
                        }
                        pubSubMenu.showMenu();

                        break;

                    case Message.READ_FROMKEYWORD_REQUEST_EVENTS:

                        ArrayList<Event> eventsListKey = message.getEventList();

                        if(eventsListKey.size()==0) {
                            System.out.println("There are no events to display");
                        }else{

                            for (Event event : eventsListKey) {
                                System.out.println("Title: " + event.getTitle());
                                System.out.println("Content: " + event.getContent());
                                System.out.println();
                            }
                        }
                        pubSubMenu.showMenu();

                        break;

                    case Message.ADVERTISE_REQUEST_TOPICS:
                        System.out.println("Topics have been advertised");
                        pubSubMenu.showMenu();
                        break;
                }

            }
        });

        udpSystem.setTimerI(new TimerI() {
            @Override
            public void timedOut() {

                    pubSubMenu.showMenu();
            }
        });

        pubSubMenu.setPubSubMenuInterface(new PubSubMenu.PubSubMenuInterface() {

            @Override
            public void invokePublish() {

                Message message = new Message();
                message.setType(Message.PUBLISH_REQUEST_TOPICS);

                try {

                    udpSystem.sendMessage(message, SERVER_IP, SERVER_PORT);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void invokeAdvertise() {

                Scanner in = new Scanner(System.in);
                System.out.print("Enter your topic to advertise:");
                String topicName = in.next();

                System.out.print("Enter your keywords:");
                String keywords = in.next();

                Message message = new Message();
                message.setTopic(new Topic(0, topicName, keywords));
                message.setType(Message.ADVERTISE_REQUEST_TOPICS);

                try {

                    //TODO
                    udpSystem.sendMessage(message, SERVER_IP, SERVER_PORT);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void invokeSubscribe() {

                Message message = new Message();
                message.setType(Message.SUBSCRIBE_REQUEST_TOPICS);

                try {

                    udpSystem.sendMessage(message, SERVER_IP, SERVER_PORT);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void invokeRead() {

                Message message = new Message();
                message.setType(Message.READ_REQUEST_EVENTS);

                try {

                    udpSystem.sendMessage(message, SERVER_IP, SERVER_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void invokeReadFromKeyword() {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the keywords:");
                String keywords = in.next();
                Message message = new Message();
                message.setTopic(new Topic(0, "test", keywords));
                message.setType(Message.READ_FROMKEYWORD_REQUEST_EVENTS);

                try {

                    //TODO
                    udpSystem.sendMessage(message, SERVER_IP, SERVER_PORT);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void invokeUnsubscribe() {

                Message message = new Message();
                message.setType(Message.UNSUB_REQUEST_TOPICS);

                try {

                    udpSystem.sendMessage(message, SERVER_IP, SERVER_PORT);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        pubSubMenu.showMenu();
    }


}
