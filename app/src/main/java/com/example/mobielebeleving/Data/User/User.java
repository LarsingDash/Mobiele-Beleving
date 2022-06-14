package com.example.mobielebeleving.Data.User;

import android.util.Log;

import com.example.mobielebeleving.Activities.LeaderboardActivity;
import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.Data.User.Achievement.Achievement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

public class User {
    public static String ID = "";
    private int points = 0;
    public static Land land;

    private Icon icon;
    private Pronoun pronoun;
    private Title title;

    private final TreeMap<String, Achievement> achievements = new TreeMap<>();
    private final File achievementsFile = new File(MainActivity.dir + "/achievements.txt");
    private final File userDataFile = new File(MainActivity.dir + "/userData.txt");

    public User(String ID) {
        this.ID = ID;
        createAchievements();

        loadAchievements();
        loadUserData();
    }

    private void loadAchievements() {
        //Start loading achievements from file
        try (Scanner scanner = new Scanner(achievementsFile)) {
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
            Objects.requireNonNull(achievements.get("Welkom!")).collect(true);

            //Debugging
            Log.println(Log.DEBUG, "DEBUG", "Achievement file did not exist");
        }
    }

    public void writeAchievements() {
        try (FileWriter writer = new FileWriter(achievementsFile)) {
            //Iterate over all achievements and write them down - Example of line: "nameAchievement|true" (true / false = isCollected)
            for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue().isCollected() + "\n");
            }

            printForDebug();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserData() {
        try (Scanner scanner = new Scanner(userDataFile)) {
            //Read userData from file
            land = new Land(scanner.nextLine());
            icon = MainActivity.icons.get(Integer.parseInt(scanner.nextLine()));
            pronoun = Pronoun.valueOf(scanner.nextLine());
            title = Title.valueOf(scanner.nextLine());
            points = Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            land = new Land("null");
            icon = MainActivity.icons.get(0);
            pronoun = Pronoun.Dappere;
            title = Title.Ridder;
            points = 0;
            writeUserData();

            //Debugging
            Log.println(Log.DEBUG, "DEBUG", "UsersData file did not exist");
        }
    }

    public void writeUserData() {
        Log.println(Log.DEBUG, "DEBUG", "Writing userData " + pronoun.name() + " " + title.name());
        try (FileWriter writer = new FileWriter(userDataFile)) {
            /*
            Example of file (default):
            Fabelwoud
            1
            Dappere
            Ridder
            0
             */

            writer.write(land.getName() + "\n");
            writer.write(icon.getID() + "\n");
            writer.write(pronoun.name() + "\n");
            writer.write(title.name() + "\n");
            writer.write(points + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAchievements() {
        achievements.put("Welkom!", new Achievement(this, new ArrayList<>(Arrays.asList(Pronoun.Dappere, Pronoun.Stoere, Title.Ridder, Title.Troll, MainActivity.icons.get(1))), "Welkom!", "Start het spel op", "Reward 1"));
        achievements.put("Profiel", new Achievement(this, new ArrayList<>(Arrays.asList(Pronoun.Magische, Pronoun.Slimme, Title.Fee, Title.Clown, MainActivity.icons.get(2))), "Profiel", "Navigeer naar jouw profiel", "Reward 2"));
        achievements.put("Debug", new Achievement(this, new ArrayList<>(Arrays.asList(Pronoun.Mysterieuze, MainActivity.icons.get(3))), "Debug", "Debug", "Reward 3"));
        achievements.put("Avonturier", new Achievement(this, new ArrayList<>(Arrays.asList(Pronoun.Ontdekkende, Title.Avonturier, MainActivity.icons.get(0))), "Avonturier", "Bekijk de details van een spel", "Ontdekkende - Avonturier"));
        }

    private void printForDebug() {
        //Print the entire list of achievements in the Log for debugging
        for (Map.Entry<String, Achievement> entry : achievements.entrySet()) {
            Log.println(Log.DEBUG, "DEBUG", entry.getKey() + " : " + entry.getValue().isCollected());
        }
    }

    public static String getID() {
        return ID;
    }

    public Icon getIcon() {
        return icon;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        LeaderboardActivity.myPoints.setText("Mijn bijdrage: " + points);
        writeUserData();
    }

    public Pronoun getPronoun() {
        return pronoun;
    }

    public Title getTitle() {
        return title;
    }

    public TreeMap<String, Achievement> getAchievements() {
        return achievements;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        writeUserData();
    }

    public void setPronoun(Pronoun pronoun) {
        this.pronoun = pronoun;
        writeUserData();
    }

    public void setTitle(Title title) {
        this.title = title;
        writeUserData();
    }

    public static Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
        writeUserData();
    }

    public File getAchievementsFile() {
        return achievementsFile;
    }

    public File getUserDataFile() {
        return userDataFile;
    }
}
