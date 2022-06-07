package com.example.mobielebeleving.data;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class Game {
    private final String name;
    private final String attraction;
    private final Drawable image;
    private int timer = 0;
    private final String description;
    private final int amountOfPoints;
    private final Point location;

    public Game(String name, String attraction, Drawable image, String description, int amountOfPoints, Point location) {
        this.name = name;
        this.attraction = attraction;
        this.image = image;
        this.description = description;
        this.amountOfPoints = amountOfPoints;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getAttraction() {
        return attraction;
    }

    public Drawable getImage() {
        return image;
    }

    public int getTimer() {
        return timer;
    }

    public String getDescription() {
        return description;
    }

    public int getAmountOfPoints() {
        return amountOfPoints;
    }

    public Point getLocation() {
        return location;
    }
}
