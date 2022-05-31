package com.example.mobielebeleving.data;

import android.location.Location;
import android.media.Image;

public class Game {
    String name;
    String attractie;
    Image image;
    int timer;
    String description;
    int amountOfPoints;
    Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttractie() {
        return attractie;
    }

    public void setAttractie(String attractie) {
        this.attractie = attractie;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmountOfPoints() {
        return amountOfPoints;
    }

    public void setAmountOfPoints(int amountOfPoints) {
        this.amountOfPoints = amountOfPoints;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
