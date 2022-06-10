package com.example.mobielebeleving.Data.User.Achievement;

import android.util.Log;
import android.widget.Toast;

import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Activities.ProfileActivity;
import com.example.mobielebeleving.Data.User.Icon;
import com.example.mobielebeleving.Data.User.Pronoun;
import com.example.mobielebeleving.Data.User.Title;
import com.example.mobielebeleving.Data.User.User;

import java.util.ArrayList;
import java.util.Collections;

public class Achievement implements Comparable<Achievement> {
    private final User user;
    private boolean isCollected;
    private final ArrayList<AchievementTarget> targets;

    private final String name;
    private final String description;
    private final String reward;

    public Achievement(User user, ArrayList<AchievementTarget> targets, String name, String description, String reward) {
        this.user = user;
        this.targets = targets;
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public Achievement(User user, AchievementTarget target, String name, String description, String reward) {
        this(user, new ArrayList<>(Collections.singletonList(target)), name, description, reward);
    }

    public void collect(boolean silent) {
        if (!isCollected) {
            isCollected = true;
            Log.println(Log.DEBUG, "DEBUG", "collected achievement " + name + " silent: " + silent);

            //Add collected targets to list of available icons / pronouns / titles
            for (AchievementTarget target : targets) {
                if (target.getClass() == Pronoun.class) {
                    ProfileActivity.availablePronouns.add((Pronoun) target);
                } else if (target.getClass() == Title.class) {
                    ProfileActivity.availableTitles.add((Title) target);
                } else {
                    ProfileActivity.availableIcons.add((Icon) target);
                }
            }

            user.writeAchievements();

            if (!silent) {
                Toast.makeText(MainActivity.context, "Achievement: \"" + name + "\" collected!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public ArrayList<AchievementTarget> getTargets() {
        return targets;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getReward() {
        return reward;
    }

    @Override
    public int compareTo(Achievement achievement) {
        //Sorting achievements so they are displayed on the profile page in the order: Collected first then uncollected. Alphabetical order after that
        if (isCollected && achievement.isCollected) {
            return Character.compare(name.charAt(0), achievement.name.charAt(0));
        } else if (isCollected) {
            return -1;
        } else {
            return 1;
        }
    }
}
