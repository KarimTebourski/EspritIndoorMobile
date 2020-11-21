package com.example.espritindoor.Model;

public class Event {

    private String idEvent;
    private String eventName;
    private String description;
    private String location;
    private String photo;
    private String date;


    public Event(String idEvent, String eventName, String description, String location, String photo, String date) {
        this.idEvent = idEvent;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.photo = photo;
        this.date = date;
    }

    public Event(String location, String eventName, String description, String photo, String date) {
        this.location = location;
        this.eventName = eventName;
        this.description = description;
        this.photo = photo;
        this.date = date;
    }

    public Event(String idEvent, String eventName, String photo) {
        this.idEvent = idEvent;
        this.eventName = eventName;
        this.photo = photo;
    }

    public Event(String eventName, String description, String photo, String date) {
        this.eventName = eventName;
        this.description = description;
        this.photo = photo;
        this.date = date;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDate() {
        return date;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", date=" + date +
                '}';
    }


}
