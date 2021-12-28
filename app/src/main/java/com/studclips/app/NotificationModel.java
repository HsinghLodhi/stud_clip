package com.studclips.app;

public class NotificationModel {

    String name,profile,notification,message;

    public NotificationModel(String name, String profile, String notification, String message) {
        this.name = name;
        this.profile = profile;
        this.notification = notification;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "NotificationModel{" +
                "name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                ", notification='" + notification + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
