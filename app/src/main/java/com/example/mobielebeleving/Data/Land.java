package com.example.mobielebeleving.Data;

import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.R;

public class Land {
    private final String name;
    private final int color;

    public static final String LegendeLand = "Legende Land";
    public static final String StoerLand = "Stoer Land";
    public static final String Fabelwoud = "Fabelwoud";

    public Land(String name) {
        this.name = name;

        //Set color according to land
        switch (name) {
            case LegendeLand:
                color = MainActivity.context.getResources().getColor(R.color.legende);
                break;

            case StoerLand:
                color = MainActivity.context.getResources().getColor(R.color.stoer);
                break;

            case Fabelwoud:
            default:
                color = MainActivity.context.getResources().getColor(R.color.fabel);
                break;
        }
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
