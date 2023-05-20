package com.example.cuvc;

public class Event implements Comparable<Event> {
    private String id;
    private String name;
    private String date;
    private String time;
    private String creationDate;
    private String creationTime;

    public Event() {

    }

    public Event(String id, String name, String date, String time, String creationDate, String creationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.creationDate = creationDate;
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }
    public void setId(String id)
    {
        this.id=id ;
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

    public String getCreationDate() {
        return creationDate;
    }

    public String getCreationTime() {
        return creationTime;
    }


    public int compareTo(Event other) {

        int dateComparison = this.getDate().compareTo(other.getDate());

        if (dateComparison == 0) {
            // If dates are the same
            return this.getTime().compareTo(other.getTime());
        } else {
            return dateComparison;
        }
    }

}
