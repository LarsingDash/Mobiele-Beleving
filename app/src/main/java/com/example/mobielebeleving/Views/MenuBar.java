package com.example.mobielebeleving.Views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.mobielebeleving.Activities.GamesActivity;
import com.example.mobielebeleving.Activities.LeaderboardActivity;
import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Activities.ProfileActivity;
import com.example.mobielebeleving.R;

import java.util.Objects;

public class MenuBar extends LinearLayout {
    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("UseCompatLoadingForDrawables")

    public MenuBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        //Set color according to Land
        setBackgroundColor(MainActivity.getUser().getLand().getColor());

        //Define width to spread out the buttons
        DisplayMetrics metrics = new DisplayMetrics();
        context.getDisplay().getMetrics(metrics);
        int width = metrics.widthPixels / 3;

        //Define attributes of all buttons
        ImageView gamesButton = new ImageView(context, attrs);
        gamesButton.setImageDrawable(context.getDrawable(R.drawable.games_icon));
        gamesButton.setMinimumWidth(width);
        addView(gamesButton);

        ImageView leaderboardButton = new ImageView(context, attrs);
        leaderboardButton.setImageDrawable(context.getDrawable(R.drawable.leaderboard_icon));
        leaderboardButton.setMinimumWidth(width);
        addView(leaderboardButton);

        ImageView profileButton = new ImageView(context, attrs);
        profileButton.setImageDrawable(context.getDrawable(R.drawable.profile_icon));
        profileButton.setMinimumWidth(width);
        addView(profileButton);

        //Click actions
        gamesButton.setOnClickListener(view -> {
            click(context, GamesActivity.class);
            Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Debug")).collect(false);
        });
        leaderboardButton.setOnClickListener(view -> click(context, LeaderboardActivity.class));
        profileButton.setOnClickListener(view -> {
            click(context, ProfileActivity.class);
            Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Profiel")).collect(false);
        });
    }

    private void click(Context context, Class<? extends Activity> target) {
        //Directing to the targeted Activity
        if (context.getClass() == target) return;

        context.startActivity(new Intent(context, target));
        ((Activity) context).finish();
    }
}
