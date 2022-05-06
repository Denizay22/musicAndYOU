package com.example.mymusicapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusicapp.R;
import com.example.mymusicapp.models.AudioModel;
import com.example.mymusicapp.models.UserModel;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class viewMusicActivity extends AppCompatActivity {

    MediaPlayer mp;
    ArrayList<AudioModel> musics;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_music);

        Bundle bundle = getIntent().getExtras();
        musics = (ArrayList<AudioModel>) bundle.getSerializable("musics");
        index = Integer.parseInt(bundle.getString("index"));


        TextView nameView = findViewById(R.id.viewmusic_musicname);
        TextView artistView = findViewById(R.id.viewmusic_artist);
        ImageView artView = findViewById(R.id.viewmusic_musiclogo);
        ImageView playButton = findViewById(R.id.viewmusic_playbutton);
        ImageView nextButton = findViewById(R.id.viewmusic_nextbutton);
        ImageView previousButton = findViewById(R.id.viewmusic_previousbutton);

        nameView.setText(musics.get(index).getName());
        artistView.setText(musics.get(index).getArtist());
        if(musics.get(index).getArt()!=null){
            artView.setImageBitmap(BitmapFactory.decodeByteArray(musics.get(index).getArt(), 0, musics.get(index).getArt().length));
        }
        mp = new MediaPlayer();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.reset();
                    mp.stop();
                }
                else{
                    mp = MediaPlayer.create(getApplicationContext(), Uri.parse(musics.get(index).getPath()));
                    mp.start();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < musics.size() - 1){
                    index++;
                    nameView.setText(musics.get(index).getName());
                    artistView.setText(musics.get(index).getArtist());
                    if(musics.get(index).getArt()!=null){
                    }
                    if(mp.isPlaying()){
                        mp.reset();
                        mp.stop();
                    }
                    else{
                        mp = MediaPlayer.create(getApplicationContext(), Uri.parse(musics.get(index).getPath()));
                        mp.start();
                    }
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index > 0){
                    index--;
                    nameView.setText(musics.get(index).getName());
                    artistView.setText(musics.get(index).getArtist());
                    if(musics.get(index).getArt()!=null){
                        artView.setImageBitmap(BitmapFactory.decodeByteArray(musics.get(index).getArt(), 0, musics.get(index).getArt().length));
                    }
                    if(mp.isPlaying()){
                        mp.reset();
                        mp.stop();
                    }
                    else{
                        mp = MediaPlayer.create(getApplicationContext(), Uri.parse(musics.get(index).getPath()));
                        mp.start();
                    }
                }
            }
        });

    }
}