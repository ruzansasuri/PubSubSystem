package edu.rit.CSCI652.impl;


import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;

import java.io.IOException;
import java.util.List;

public class EventManager implements ServerI{
	
	/*
	 * Start the repo service
	 */
	private void startService()
	{


//		UDPSystem updServer = new UDPSystem();


		UDPSystem udpSystem = null;
		udpSystem = new UDPSystem();
		udpSystem.getMessages(this);

		// while true loop

		/*

			// db
			topic table same as model
			publisher table
			subscribe table
			even table - same as model
			publisher-event table - publisher id, event id
			subscriber-topic table - subscriber id, topic id

		 // pub-sub person menu

		 - publish
		 	- list out all topics
		 	- select topic
		 	- write title, content for the event
		 	- publish
		 - advertise
		 	- write name of new topic - name and keywords
		 	- add subscriber to list
		 - read
		 	- get messages from the event manager
		 - subscribe
		 	- all topics that he hasnt subbed to
		 	- select and subscribe - one entry in sub table, and one in sub-topic table
		 - un-subscribe
		 	- all topics that he has subbed to
		 	- select and unsub - remove from sub-topic topic, remove subscriber from the list



		 // event manager

		 - advertise
		 	- get {name, topic name, keywords, type=adversise}
		 	- if he is not there, add him, and add the topic with the keywords if it does not exists, else reject
		 	- store, topic id, topic name, keywords
		 - sub
		 	- get {name, type=subscribe}
		 	- send {list of all topics}
		 	- get {name, topic}
		 	- if he doesn't exist add him
		 	- add to sub-topic db (id, topic-id)

		- publish
			- get {name, type=publish}
			- send {list of all topics}
		 	- get {name, topic, title, content}
		 	- store in event table, {topic id, title, content, publisher id, time stamp}
		 	- if topic per events > 10, delete the old ones

		- read
			- get {name, type=read}
			- query subscriber-topic table -
			- for each topic, get events from events table , using the events-topic table
			- send {event - topic name, title, content from last event id,  if not event id, sort by time stamp, give last 10}
			- update the sub table with the last timestamp
		 */


//		Server server = new Server();
//		server.sendMessage("some message", new ServerI() {
//			public void getMessage(String message) {
//
//			}
//		});
	}

	/*
	 * notify all subscribers of new event 
	 */
	private void notifySubscribers(Event event) {
		
	}
	
	/*
	 * add new topic when received advertisement of new topic
	 */
	private void addTopic(Topic topic){
		
	}
	
	/*
	 * add subscriber to the internal list
	 */
	private void addSubscriber(){
		
	}

	public void subscribersPing(Subscriber subscriber)
	{
		System.out.println("You were pinged.");
	}

	public void newEvents(List<Event> events)
	{

	}

	/*
	 * remove subscriber from the list
	 */
	private void removeSubscriber(){
		
	}
	
	/*
	 * show the list of subscriber for a specified topic
	 */
	private void showSubscribers(Topic topic){
		
	}

	@Override
	public void success(String message)
	{

	}
	
	public static void main(String[] args)
	{
		new EventManager().startService();
	}


}
