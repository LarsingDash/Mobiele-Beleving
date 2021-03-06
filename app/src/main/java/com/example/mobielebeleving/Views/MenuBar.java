package com.example.mobielebeleving.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.mobielebeleving.Activities.GamesActivity;
import com.example.mobielebeleving.Activities.LeaderboardActivity;
import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Activities.ProfileActivity;
import com.example.mobielebeleving.R;

import java.util.Objects;

public class MenuBar extends LinearLayout {
    private static int width = 0;
    private static int height = 0;

    public MenuBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setOrientation(HORIZONTAL);
        } else {
            setOrientation(VERTICAL);
        }
        //Set color according to Land
        setBackgroundColor(MainActivity.getUser().getLand().getColor());

        //Find all buttons
        ImageView gamesButton = new ImageView(context, attrs);
        ImageView leaderboardButton = new ImageView(context, attrs);
        ImageView profileButton = new ImageView(context, attrs);

        //Spread out the buttons
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = getWidth();
                height = getHeight();

                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    gamesButton.setMinimumWidth(width / 3);
                    leaderboardButton.setMinimumWidth(width / 3);
                    profileButton.setMinimumWidth(width / 3);
                } else {
                    gamesButton.setMinimumHeight(height / 3);
                    leaderboardButton.setMinimumHeight(height / 3);
                    profileButton.setMinimumHeight(height / 3);
                }
            }
        });

        //Define attributes of all buttons
        gamesButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.games_icon));
        addView(gamesButton);

        leaderboardButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.leaderboard_icon));
        addView(leaderboardButton);

        profileButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.profile_icon));
        addView(profileButton);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gamesButton.setMinimumWidth(width / 3);
            leaderboardButton.setMinimumWidth(width / 3);
            profileButton.setMinimumWidth(width / 3);
        } else {
            gamesButton.setMinimumHeight(height / 3);
            leaderboardButton.setMinimumHeight(height / 3);
            profileButton.setMinimumHeight(height / 3);
        }

        //Click actions
        gamesButton.setOnClickListener(view -> {
            click(context, GamesActivity.class);
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
