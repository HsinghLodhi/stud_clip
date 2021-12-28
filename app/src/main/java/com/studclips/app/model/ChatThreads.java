package com.studclips.app.model;

import java.io.Serializable;

public class ChatThreads implements Serializable {
    String name, message, image, time;
    int messageCount;

    public ChatThreads(String name, String message, String image, String time, int messageCount) {
        this.name = name;
        this.message = message;
        this.image = image;
        this.time = time;
        this.messageCount = messageCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}
