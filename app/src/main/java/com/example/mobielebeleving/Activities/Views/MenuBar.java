package com.example.mobielebeleving.Activities.Views;

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
import com.example.mobielebeleving.Activities.ProfileActivity;
import com.example.mobielebeleving.R;

public class MenuBar extends LinearLayout {
    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("UseCompatLoadingForDrawables")

    public MenuBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.purple_700));

        DisplayMetrics metrics = new DisplayMetrics();
        context.getDisplay().getMetrics(metrics);
        int width = metrics.widthPixels / 3;

        ImageView gamesButton = new ImageView(context, attrs);
        gamesButton.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
        gamesButton.setMinimumWidth(width);
        addView(gamesButton);

        ImageView leaderboardButton = new ImageView(context, attrs);
        leaderboardButton.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
        leaderboardButton.setMinimumWidth(width);
        addView(leaderboardButton);

        ImageView profileButton = new ImageView(context, attrs);
        profileButton.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
        profileButton.setMinimumWidth(width);
        addView(profileButton);

        gamesButton.setOnClickListener(view -> click(context, GamesActivity.class));
        leaderboardButton.setOnClickListener(view -> click(context, LeaderboardActivity.class));
        profileButton.setOnClickListener(view -> click(context, ProfileActivity.class));
    }

    private void click(Context context, Class<? extends Activity> target) {
        if (context.getClass() == target) return;

        context.startActivity(new Intent(context, target));
        ((Activity) context).finish();
    }
}
