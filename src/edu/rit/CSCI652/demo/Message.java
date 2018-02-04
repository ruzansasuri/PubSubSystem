package edu.rit.CSCI652.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruzan on 2/1/2018.
 */
public class Message
{
    private int type;
    private ArrayList<Topic> topicList;
    private ArrayList<Event> eventList;
    private Subscriber subscriber;

    public static final int REQUEST_TOPICS = 1;



    public Message(){

    }

    public Message(int type, ArrayList<Topic> topicList, ArrayList<Event> eventList, Subscriber subscriber)
    {
        this.type = type;
        this.topicList = topicList;
        this.eventList = eventList;
        this.subscriber = subscriber;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public ArrayList<Topic> getTopicList()
    {
        return topicList;
    }

    public void setTopicList(ArrayList<Topic> topicList)
    {
        this.topicList = topicList;
    }

    public ArrayList<Event> getEventList()
    {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList)
    {
        this.eventList = eventList;
    }

    public Subscriber getSubscriber()
    {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber)
    {
        this.subscriber = subscriber;
    }

}
