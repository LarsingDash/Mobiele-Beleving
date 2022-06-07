package com.example.mobielebeleving.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobielebeleving.R;
import com.example.mobielebeleving.data.Game;

public class DetailViewAdapter extends RecyclerView.Adapter<DetailViewAdapter.DetailViewHolder>{
    private Context context;
    private Game game;

    public DetailViewAdapter(Context context, Game game) {
        this.context = context;
        this.game = game;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_view, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.photo.setImageDrawable(game.getImage());
        holder.name.setText(game.getName());
        holder.attraction.setText(game.getAttraction());
        holder.description.setText(game.getDescription());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView attraction;
        TextView description;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.detailGamePhoto);
            name = itemView.findViewById(R.id.detailGameName);
            attraction = itemView.findViewById(R.id.detailGameAttraction);
            description = itemView.findViewById(R.id.detailGameDescription);
        }
    }
}
