package com.example.mobielebeleving.Data;

import android.util.Log;

import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Data.Achievement.Achievement;
import com.example.mobielebeleving.Data.Achievement.Pronoun;
import com.example.mobielebeleving.Data.Achievement.Title;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class User {
    private final String ID;
    private int points;
    private Pronoun pronoun;
    private Title title;
    private final HashMap<String, Achievement> achievements = new HashMap<>();
    private final File file = new File(MainActivity.dir + "/achievements.txt");

    public User(String ID) {
        this.ID = ID;
        createAchievements();

        try (Scanner scanner = new Scanner(file)) {
            String firstLine = scanner.nextLine();

            if (firstLine.equals("empty")) {
                scanner.close();
                firstTime();
            } else {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("true")) {
                        Objects.requireNonNull(achievements.get(line.substring(0, line.indexOf('|')))).collect(true);
                    }

                }

                for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
                    Log.println(Log.DEBUG, "DEBUG", entry.getKey() + " : " + entry.getValue().isCollected());
                }
                Log.println(Log.DEBUG, "DEBUG", firstLine);
            }
        } catch (FileNotFoundException e) {
            firstTime();
            e.printStackTrace();
        }
    }

    private void firstTime() {
        writeAchievements();

        Objects.requireNonNull(achievements.get("ac1")).collect(true);
        Objects.requireNonNull(achievements.get("ac2")).collect(true);
    }

    public String getID() {
        return ID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Pronoun getPronoun() {
        return pronoun;
    }

    public Title getTitle() {
        return title;
    }

    public HashMap<String, Achievement> getAchievements() {
        return achievements;
    }

    public void writeAchievements() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Achievement\n");

            for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue().isCollected() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAchievements() {
        achievements.put("ac1", new Achievement(this, Pronoun.Slimme, "AC1", "1"));
        achievements.put("ac2", new Achievement(this, Pronoun.Dappere, "AC2", "2"));
        achievements.put("ac3", new Achievement(this, Title.Fee, "AC3", "3"));
        achievements.put("ac4", new Achievement(this, Title.Clown, "AC4", "4"));
    }
}
