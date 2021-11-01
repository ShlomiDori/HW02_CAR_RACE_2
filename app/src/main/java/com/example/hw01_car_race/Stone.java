package com.example.hw01_car_race;

public class Stone {

    private int res = 0;
    private boolean isStone = false;

    public Stone() { }

    public int getRes() {
        return res;
    }

    public Stone setRes(int res) {
        this.res = res;
        return this;
    }

    public boolean isStone() {
        return isStone;
    }

    public Stone setStone(boolean chicken) {
        isStone= chicken;
        return this;
    }
}
