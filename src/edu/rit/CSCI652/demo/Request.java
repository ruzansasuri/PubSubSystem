package edu.rit.CSCI652.demo;

import java.util.List;

/**
 * Created by Ruzan on 2/1/2018.
 */
public class Request
{
    private int type;
    private List<Topic> topicList;
    private List<Event> eventList;
    private Subscriber subscriber;

    public Request(int type, List<Topic> topicList, List<Event> eventList, Subscriber subscriber)
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

    public List<Topic> getTopicList()
    {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList)
    {
        this.topicList = topicList;
    }

    public List<Event> getEventList()
    {
        return eventList;
    }

    public void setEventList(List<Event> eventList)
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
