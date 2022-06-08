package com.example.mobielebeleving.Data.User;

import android.util.Log;

import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Data.User.Achievement.Achievement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class User {
    private final String ID;
    private int points;

    private Icon icon;
    private Pronoun pronoun;
    private Title title;

    private final HashMap<String, Achievement> achievements = new HashMap<>();
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
            icon = MainActivity.icons.get(Integer.parseInt(scanner.nextLine()));
            pronoun = Pronoun.valueOf(scanner.next());
            title = Title.valueOf(scanner.next());
        } catch (FileNotFoundException e) {
            icon = MainActivity.icons.get(1);
            pronoun = Pronoun.Dappere;
            title = Title.Ridder;
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
            1
            Dappere
            Ridder
             */

            writer.write(icon.getID() + "\n");
            writer.write(pronoun.name() + "\n");
            writer.write(title.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAchievements() {
        achievements.put("Welkom!", new Achievement(this, new ArrayList<>(Arrays.asList(Pronoun.Dappere, Pronoun.Stoere, Title.Ridder, Title.Troll)), "Welkom!", "Start het spel op"));
        achievements.put("Profiel", new Achievement(this, new ArrayList<>(Arrays.asList(Pronoun.Magische, Pronoun.Slimme, Title.Fee, Title.Clown)), "Profiel", "Navigeer naar jouw profiel"));
        achievements.put("Debug", new Achievement(this, Pronoun.Mysterieuze, "Debug", "jebollemama"));
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

    public Icon getIcon() {
        return icon;
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

    public File getAchievementsFile() {
        return achievementsFile;
    }

    public File getUserDataFile() {
        return userDataFile;
    }
}
