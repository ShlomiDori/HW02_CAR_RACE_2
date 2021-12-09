package com.example.hw02_car_race_2.objects;

import java.util.Date;

public class Record {

    private String name;
    private int score;
    private double lat;
    private double lng;
    private Date date;

    public Record() {
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Record setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Record setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        if (name == null)
            return score + " pts, " + date.getDay() + "/" + date.getMonth();
        return name + ": "  + score + " pts, " + date.getDay() + "/" + date.getMonth();
    }

}