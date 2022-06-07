package com.example.mobielebeleving.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.Activities.DetailActivity;
import com.example.mobielebeleving.R;
import com.example.mobielebeleving.data.Game;

import java.util.ArrayList;

public class GameViewAdapter extends RecyclerView.Adapter<GameViewAdapter.GameViewHolder> {
    private final Context context;
    private final ArrayList<Game> games;

    public GameViewAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_view_row, parent, false);
        return new GameViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);

        holder.photo.setImageDrawable(game.getImage());
        holder.name.setText(game.getName());
        holder.attraction.setText(game.getAttraction());

        holder.layout.setOnClickListener(view -> click(position));
        holder.button.setOnClickListener(view -> click(position));

        DisplayMetrics metrics = new DisplayMetrics();
        context.getDisplay().getMetrics(metrics);
        holder.layout.setMinHeight((metrics.heightPixels - 200) / getItemCount());
    }

    private void click(int position) {
        context.startActivity(new Intent(context, DetailActivity.class).putExtra("index", position));
        ((Activity) context).finish();
    }

    @Override
    public int getItemCount() {
        if (games == null) {
            return 0;
        } else {
            return games.size();
        }
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView attraction;
        Button button;
        ConstraintLayout layout;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.gamePhoto);
            name = itemView.findViewById(R.id.gameName);
            attraction = itemView.findViewById(R.id.gameAttraction);
            button = itemView.findViewById(R.id.gameButton);
            layout = itemView.findViewById(R.id.gameViewItem);
        }
    }
}
