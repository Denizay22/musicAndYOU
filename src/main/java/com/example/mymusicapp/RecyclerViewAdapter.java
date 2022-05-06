package com.example.mymusicapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusicapp.activity.viewMusicActivity;
import com.example.mymusicapp.models.AudioModel;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<AudioModel> musics;

    public RecyclerViewAdapter(ArrayList<AudioModel> musics){
        this.musics = musics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AudioModel music = musics.get(position);

        holder.name.setText(music.getName());
        holder.artist.setText(music.getArtist());
        holder.duration.setText(music.getDuration());

        if(music.getArt()!=null){
            holder.albumArt.setImageBitmap(BitmapFactory.decodeByteArray(music.getArt(), 0, music.getArt().length));
        }
        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open music big
                Bundle b = new Bundle();
                b.putSerializable("musics", musics);
                b.putString("index", String.valueOf(holder.getAdapterPosition()));
                Intent i = new Intent(holder.cardViewItem.getContext(), viewMusicActivity.class);
                i.putExtras(b);
                holder.cardViewItem.getContext().startActivity(i);
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete music
                File f = new File(musics.get(holder.getAdapterPosition()).getPath());
                f.delete();
                musics.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                }
        });

        holder.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo share
            }
        });
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardViewItem;
        ImageView albumArt;
        ImageView deleteIcon;
        ImageView shareIcon;
        TextView name;
        TextView artist;
        TextView duration;

        public ViewHolder(View itemView) {
            super(itemView);

            cardViewItem = itemView.findViewById(R.id.music_list_item);
            albumArt = itemView.findViewById(R.id.music_list_item_art);
            deleteIcon = itemView.findViewById(R.id.music_list_item_delete);
            shareIcon = itemView.findViewById(R.id.music_list_item_share);
            name = itemView.findViewById(R.id.music_list_item_name);
            artist = itemView.findViewById(R.id.music_list_item_artist);
            duration = itemView.findViewById(R.id.music_list_item_duration);
        }

        public CardView getCardView(){ return cardViewItem; }
    }

}