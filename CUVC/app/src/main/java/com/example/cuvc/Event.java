package com.example.cuvc;

public class Event implements Comparable<Event> {
    private String name;
    private String date;
    private String time;
    private String Id;

    public Event(String Id,String name, String date, String time) {
        this.Id=Id ;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return Id;
    }
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int compareTo(Event other) {
        // Compare events based on time
        return this.getTime().compareTo(other.getTime());
    }
}
