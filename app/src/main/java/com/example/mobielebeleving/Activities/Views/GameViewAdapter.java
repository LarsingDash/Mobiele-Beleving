package com.example.mobielebeleving.Activities.Views;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.photo.setImageDrawable(games.get(position).getImage());
        holder.name.setText(games.get(position).getName());
        holder.attraction.setText(games.get(position).getAttraction());

        holder.layout.setOnClickListener(view -> {
            Toast.makeText(context, games.get(position).getName() + " Detail Scherm", Toast.LENGTH_SHORT).show();
        });
        holder.button.setOnClickListener(view -> {
            Toast.makeText(context, games.get(position).getName() + " Detail Scherm", Toast.LENGTH_SHORT).show();
        });

        DisplayMetrics metrics = new DisplayMetrics();
        context.getDisplay().getMetrics(metrics);
        holder.layout.setMinHeight((metrics.heightPixels - 200) / getItemCount());
    }

    @Override
    public int getItemCount() {
        if (games == null) {
            return 0;
        } else {
            return games.size();
        }
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
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
