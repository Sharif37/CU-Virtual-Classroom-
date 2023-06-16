package com.example.cuvc;

import java.time.LocalTime;

public class TrainSchedule {
    private String Time;
    private String backgroundColor;
    private String fromCampus,fromCity ;

    public TrainSchedule(String Time) {
        this.Time = Time;


    }

    public String getTime() {
        return Time;
    }

    public String getFromCampus() {
        return fromCampus;
    }

    public void setFromCampus(String fromCampus) {
        this.fromCampus = fromCampus;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
