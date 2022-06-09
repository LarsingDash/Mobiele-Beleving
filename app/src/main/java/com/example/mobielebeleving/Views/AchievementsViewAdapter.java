package com.example.mobielebeleving.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.Data.User.Achievement.Achievement;
import com.example.mobielebeleving.R;

import java.util.ArrayList;
import java.util.Collections;

public class AchievementsViewAdapter extends RecyclerView.Adapter<AchievementsViewAdapter.AchievementsViewHolder> {
    private final Context context;
    private final ArrayList<Achievement> achievements;

    public AchievementsViewAdapter(Context context, ArrayList<Achievement> achievements) {
        this.context = context;
        this.achievements = achievements;
        Collections.sort(achievements);

        for (Achievement achievement : achievements) {
            Log.println(Log.DEBUG, "DEBUG", achievement.getName() + " : " + achievement.isCollected());
        }
    }

    @NonNull
    @Override
    public AchievementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate predefined layout to context-size
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.achievements_view_row, parent, false);
        return new AchievementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementsViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        
        //Set attributes for all views according to the achievement
        Drawable status;
        if (achievement.isCollected()) {
            status = AppCompatResources.getDrawable(context, R.drawable.check);
        } else {
            status = AppCompatResources.getDrawable(context, R.drawable.cross);
        }
        holder.status.setImageDrawable(status);
        holder.name.setText(achievement.getName());
        holder.description.setText(achievement.getDescription());
        holder.reward.setText(achievement.getReward());
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class AchievementsViewHolder extends RecyclerView.ViewHolder {
        ImageView status;
        TextView name;
        TextView description;
        TextView reward;

        public AchievementsViewHolder(@NonNull View itemView) {
            super(itemView);

            //Finding all views
            status = itemView.findViewById(R.id.achievementStatus);
            name = itemView.findViewById(R.id.achievementName);
            description = itemView.findViewById(R.id.achievementDescription);
            reward = itemView.findViewById(R.id.achievementReward);
        }
    }
}
