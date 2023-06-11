package com.example.cuvc;

import java.time.LocalTime;

public class TrainSchedule {
    private String name;
    private String fromCampus,fromCity ;

    public TrainSchedule(String name, String fromCampus) {
        this.name = name;
        this.fromCampus=fromCampus ;

    }

    public String getName() {
        return name;
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
}
