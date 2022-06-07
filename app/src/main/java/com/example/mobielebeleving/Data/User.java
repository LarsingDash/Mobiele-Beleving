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

        //Start loading achievements from file
        try (Scanner scanner = new Scanner(file)) {
            //If the file didn't exist the scanner would've crashed and got caught, so assuming the file exists it will start parsing the data
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                //if achievement should be completed = silently complete it
                if (line.contains("true")) {
                    Objects.requireNonNull(achievements.get(line.substring(0, line.indexOf('|')))).collect(true);
                }
            }

            printForDebug();
        } catch (FileNotFoundException e) {
            //In case the file does not exist = collect default achievement(s)
            Objects.requireNonNull(achievements.get("ac1")).collect(true);
            Objects.requireNonNull(achievements.get("ac2")).collect(true);

            //Debugging
            Log.println(Log.DEBUG, "DEBUG", "File did not exist");
        }
    }

    public void writeAchievements() {
        try (FileWriter writer = new FileWriter(file)) {
            //Iterate over all achievements and write them down - Example of line: "nameAchievement|true" (true / false = isCollected)
            for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue().isCollected() + "\n");
            }

            printForDebug();
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

    private void printForDebug() {
        //Print the entire list of achievements in the Log for debugging
        for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
            Log.println(Log.DEBUG, "DEBUG", entry.getKey() + " : " + entry.getValue().isCollected());
        }
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
}
