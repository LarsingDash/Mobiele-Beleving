package com.example.mobielebeleving.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.Activities.LeaderboardActivity;
import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.MQTT.TopicHandler;
import com.example.mobielebeleving.R;

import java.util.ArrayList;

public class LandAdapter extends RecyclerView.Adapter<LandAdapter.LandHolder> {
    private final Context context;
    private final ArrayList<Land> lands;

    public LandAdapter(Context context, ArrayList<Land> lands) {
        this.context = context;
        this.lands = lands;
    }

    @NonNull
    @Override
    public LandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate predefined layout to context-size
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.land_item, parent, false);
        return new LandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LandHolder holder, int position) {
        Land land = lands.get(position);

        if (land.getName().equals("INFO")) holder.button.setVisibility(View.GONE);


        //Set attributes for all views according to the Land
        holder.layout.setBackgroundColor(land.getColor());
        holder.name.setText(land.getName());
        holder.story.setText(land.getStory());
        holder.button.setOnClickListener(view -> {
            MainActivity.getUser().setLand(land);
            context.startActivity(new Intent(context, LeaderboardActivity.class));
            TopicHandler.confirmLandChoice();
            ((Activity) context).finish();
        });
        holder.button.setBackgroundColor(context.getResources().getColor(R.color.white));
        holder.button.setTextColor(land.getColor());
    }

    @Override
    public int getItemCount() {
        return lands.size();
    }

    public static class LandHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView name;
        TextView story;
        Button button;

        public LandHolder(@NonNull View itemView) {
            super(itemView);

            //Finding all views
            layout = itemView.findViewById(R.id.landLayout);
            name = itemView.findViewById(R.id.landName);
            story = itemView.findViewById(R.id.landStory);
            button = itemView.findViewById(R.id.landChoose);
        }
    }
}
