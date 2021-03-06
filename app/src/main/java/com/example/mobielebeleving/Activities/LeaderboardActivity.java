package com.example.mobielebeleving.Activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.Data.User.User;
import com.example.mobielebeleving.R;

public class LeaderboardActivity extends AppCompatActivity {
    private static int highest = 0;
    private static int height = -1;
    private static LeaderboardActivity activity;

    public static int legendeland = 0;
    public static int stoerland = 0;
    public static int fabelwoud = 0;

    public static TextView myPoints;
    private static int dimen = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) dimen = 200;
        else dimen = 100;

        activity = this;
        myPoints = activity.findViewById(R.id.leaderMyPoints);
        myPoints.setBackgroundColor(User.getLand().getColor());
        myPoints.setText("Mijn bijdrage: " + User.getPoints());

        setBar(Land.Legendeland, legendeland);
        setBar(Land.Stoerland, stoerland);
        setBar(Land.Fabelwoud, fabelwoud);

        MainActivity.canSubscribe = true;
    }

    public static void setBar(String land, int points) {
        ConstraintLayout bar;
        TextView counter;
        ConstraintLayout background;

        //Get the bar and counter according to the land specified
        switch (land) {
            case Land.Legendeland:
                bar = activity.findViewById(R.id.leaderLegende);
                counter = activity.findViewById(R.id.leaderLegendeCounter);
                background = activity.findViewById(R.id.leaderLegendeBackground);
                legendeland = points;
                break;

            case Land.Stoerland:
                bar = activity.findViewById(R.id.leaderStoer);
                counter = activity.findViewById(R.id.leaderStoerCounter);
                background = activity.findViewById(R.id.leaderStoerBackground);
                stoerland = points;
                break;

            default:
            case Land.Fabelwoud:
                bar = activity.findViewById(R.id.leaderFabel);
                counter = activity.findViewById(R.id.leaderFabelCounter);
                background = activity.findViewById(R.id.leaderFabelBackground);
                fabelwoud = points;
        }

        //Replace highest if record is beaten
        if (points > highest) highest = points;

        //Getting the total height of the bar so that the bar can be scaled properly
        bar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                bar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //Make the bar thinner
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bar.getLayoutParams();
                params.setMargins(dimen, 0, dimen, 0);
                bar.requestLayout();

                //Push the counter down (and the bar along with it)
                ViewGroup.MarginLayoutParams paramsCounter = (ViewGroup.MarginLayoutParams) counter.getLayoutParams();

                if (height == -1) height = bar.getHeight();
                double percentage = points / (double) highest;

                //Calculate the distance
                paramsCounter.setMargins(0, (int) (height - height * percentage), 0, 0);

                //Set counter to score
                counter.setText(points + "");
            }
        });

        if (User.getLand().getName().equals(land)) {
            background.setBackgroundColor(User.getLand().getColor());
        } else {
            background.setBackgroundColor(activity.getResources().getColor(R.color.gray));
        }

        //Update the view
        counter.requestLayout();
    }
}