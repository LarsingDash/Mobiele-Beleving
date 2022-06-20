package com.example.mobielebeleving.Data;

import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.R;

public class Land {
    private final String name;
    private final String story;
    private final int color;

    public static final String Legendeland = "Legendeland";
    public static final String Stoerland = "Stoerland";
    public static final String Fabelwoud = "Fabelwoud";

    public Land(String name) {
        this.name = name;

        //Set color according to land
        switch (name) {
            case Legendeland:
                story = MainActivity.context.getResources().getString(R.string.legendeland);
                color = MainActivity.context.getResources().getColor(R.color.legende);
                break;

            case Stoerland:
                story = MainActivity.context.getResources().getString(R.string.stoerland);
                color = MainActivity.context.getResources().getColor(R.color.stoer);
                break;

            case Fabelwoud:
                story = MainActivity.context.getResources().getString(R.string.fabelwoud);
                color = MainActivity.context.getResources().getColor(R.color.fabel);
                break;

            default:
                story = MainActivity.context.getResources().getString(R.string.landInfo);
                color = MainActivity.context.getResources().getColor(R.color.really_dark_gray);
        }
    }

    public String getName() {
        return name;
    }

    public String getStory() {
        return story;
    }

    public int getColor() {
        return color;
    }
}
