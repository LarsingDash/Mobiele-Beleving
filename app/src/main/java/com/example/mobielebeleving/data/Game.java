package com.example.mobielebeleving.data;

import android.location.Location;
import android.media.Image;

public class Game {
    private String name;
    private String attraction;
    private Image image;
    private int timer;
    private String description;
    private int amountOfPoints;
    private Location location;

    public Game(String name, String attraction, Image image, int timer, String description, int amountOfPoints, Location location) {
        this.name = name;
        this.attraction = attraction;
        this.image = image;
        this.timer = timer;
        this.description = description;
        this.amountOfPoints = amountOfPoints;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttraction() {
        return attraction;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
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
