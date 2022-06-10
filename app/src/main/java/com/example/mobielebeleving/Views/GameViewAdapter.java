package com.example.mobielebeleving.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.Activities.DetailActivity;
import com.example.mobielebeleving.Activities.MainActivity;
import com.example.mobielebeleving.Data.Game;
import com.example.mobielebeleving.R;

import java.util.ArrayList;
import java.util.Objects;

public class GameViewAdapter extends RecyclerView.Adapter<GameViewAdapter.GameViewHolder> {
    private final Context context;
    private final ArrayList<Game> games;

    private final ArrayList<GameViewHolder> holders = new ArrayList<>();

    public GameViewAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate predefined layout to context-size
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_view_row, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);
        holders.add(holder);

        //Set attributes for all views according to the game
        holder.photo.setImageDrawable(game.getImage());
        holder.name.setText(game.getName());
        holder.attraction.setText(game.getAttraction());

        holder.layout.setOnClickListener(view -> click(position));
        holder.button.setOnClickListener(view -> click(position));

        //Change the color of the button according to the Land
        holder.button.setBackgroundColor(MainActivity.getUser().getLand().getColor());
    }

    public void spreadOut(int height) {
        //Spreading out the items
        for (GameViewHolder holder : holders) {
            holder.layout.setMinHeight(height / getItemCount());
        }
    }

    private void click(int position) {
        //Start DetailActivity
        context.startActivity(new Intent(context, DetailActivity.class).putExtra("index", position));
        Objects.requireNonNull(MainActivity.getUser().getAchievements().get("Avonturier")).collect(false);
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

            //Finding all views
            photo = itemView.findViewById(R.id.gamePhoto);
            name = itemView.findViewById(R.id.gameName);
            attraction = itemView.findViewById(R.id.gameAttraction);
            button = itemView.findViewById(R.id.gameButton);
            layout = itemView.findViewById(R.id.gameViewItem);
        }
    }
}
