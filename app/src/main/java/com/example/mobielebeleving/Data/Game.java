package com.example.mobielebeleving.Data;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class Game {
    private final String name;
    private final String attraction;
    private final Drawable image;
    private int timer = 0;
    private final String story;
    private final String explanation;
    private final Point location;

    public Game(String name, String attraction, Drawable image, String story, String explanation, Point location) {
        this.name = name;
        this.attraction = attraction;
        this.image = image;
        this.story = story;
        this.explanation = explanation;
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

    public String getStory() {
        return story;
    }

    public String getExplanation() {
        return explanation;
    }

    public Point getLocation() {
        return location;
    }
}
