package com.example.mobielebeleving.Data.User;

import android.graphics.drawable.Drawable;

import com.example.mobielebeleving.Data.User.Achievement.AchievementTarget;

public class Icon implements AchievementTarget {
    private final int ID;
    private final Drawable icon;

    public Icon(int ID, Drawable icon) {
        this.ID = ID;
        this.icon = icon;
    }

    public int getID() {
        return ID;
    }

    public Drawable getIcon() {
        return icon;
    }
}
