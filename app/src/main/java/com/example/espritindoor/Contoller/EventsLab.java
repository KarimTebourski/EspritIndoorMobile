package com.example.espritindoor.Contoller;

import com.example.espritindoor.Model.Event;

import java.util.ArrayList;
import java.util.List;


public class EventsLab {



    private static EventsLab sEventsLab;
    private List<Event> mEvents;



    public static EventsLab get() {
        if (sEventsLab == null) {
            sEventsLab   = new EventsLab();
        }
        return sEventsLab;
    }

    private EventsLab() {






    }


    public List<Event> getmEvents() {
        return mEvents;
    }

    public List<Event> getEventCopy() {
        return new ArrayList<>(mEvents);
    }

    public void addEvent(Event event) {
        mEvents.add(event);
    }

    public Event getEvent(String id) {
        for (Event event : mEvents) {
            if (event.getIdEvent() == id) {
                return event;
            }
        }

        return null;
    }










}
