package com.example.mobielebeleving.Data.Achievement;

import android.util.Log;

import com.example.mobielebeleving.Data.User;

public class Achievement {
    private final User user;
    private boolean isCollected;
    private final AchievementTarget target;

    private final String name;
    private final String description;

    public Achievement(User user, AchievementTarget target, String name, String description) {
        this.user = user;
        this.target = target;
        this.name = name;
        this.description = description;
    }

    public void collect(boolean silent) {
        isCollected = true;
        Log.println(Log.DEBUG, "DEBUG", "collected achievement " + name + " silent: " + silent);
        user.writeAchievements();

        if (!silent) {
            //todo popup
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public AchievementTarget getTarget() {
        return target;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
