package com.example.mobielebeleving;

public class User {
    private int iD;
    private int points;

    public User(int iD, int points) {
        this.iD = iD;
        this.points = points;
    }

    public int getiD(){
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
