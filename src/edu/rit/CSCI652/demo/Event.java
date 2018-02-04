package edu.rit.CSCI652.demo;

import java.util.List;

public class Event {

    public Event(int topicId, String title, String content, long publishDateTime) {

        this.topicId = topicId;
        this.title = title;
        this.content = content;
        this.publishDateTime = publishDateTime;
    }


    private int topicId;
    private String title;
    private String content;
    private long publishDateTime;

    public int getTopicId() {
        return topicId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getPublishDateTime() {
        return publishDateTime;
    }

    @Override
    public String toString() {
        return topicId + "," + title + "," + content;
    }
}
