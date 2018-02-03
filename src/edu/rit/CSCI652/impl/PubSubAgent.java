package edu.rit.CSCI652.impl;

import com.google.gson.Gson;
import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Request;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PubSubAgent implements PublisherI, SubscriberI, ServerI
{
	// Make PubSubAgent and Event Manager the controllers and make the system an anonymous object.
	String eventManagerIP;
	UDPSystem udpSystem;

	public PubSubAgent(String eventManagerIP) throws IOException
	{
//		eventManagerIP  = "localhost";
//		subscriberController = new SubscriberController(eventManagerIP);
		this.eventManagerIP = eventManagerIP;
		UDPSystem udpSystem = new UDPSystem();
		udpSystem.getMessages(this);
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
		// TODO Auto-generated method stub
//		subscribedTopics.put(topic.getId(), topic);
		send(topic);
		//db stuff
	}

	@Override
	public void subscribe(String keyword)
	{
	}

	@Override
	public void unsubscribe(Topic topic)
	{
		// TODO Auto-generated method stub
//		subscribedTopics.remove(topic.getId());
		send(topic);
		//db stuff
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
		// TODO Auto-generated method stub
		send(event);
	}

	@Override
	public void advertise(Topic newTopic)
	{
		// TODO Auto-generated method stub
//		subscribedTopics.put(newTopic.getId(), newTopic);// We assume that a publisher who advertises a new topic wants
														 // to subscribe to it too.
		send(newTopic);
	}

	@Override
	public void success(String message)
	{
		Gson gson = new Gson();
		Request request = gson.fromJson(message, Request.class);
		switch(request.getType())
		{
			case 0:
				displayTopics(request.getTopicList());
				break;
			case 1:
				displayEvents(request.getEventList());
				break;
			case 2:
		}
	}
	public void send(Topic topic)
	{
		try
		{
			udpSystem.sendEcho("", udpSystem.getEventManagerIP());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void send(Subscriber subscriber)
	{

	}
	public void send(Event event)
	{

	}
	
}
