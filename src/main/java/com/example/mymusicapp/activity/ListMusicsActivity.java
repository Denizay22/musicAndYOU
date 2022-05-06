package com.example.mymusicapp.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.example.mymusicapp.R;
import com.example.mymusicapp.RecyclerViewAdapter;
import com.example.mymusicapp.models.UserModel;
import com.example.mymusicapp.models.AudioModel;
import java.io.File;
import java.util.ArrayList;

public class ListMusicsActivity extends AppCompatActivity {

    private final static int WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = 0;
    ArrayList<File> musicsFiles;
    ArrayList<AudioModel> musics;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_musics);


        Bundle bundle = getIntent().getExtras();
        UserModel logged_in_user = (UserModel) bundle.getSerializable("logged_in_user");

        recyclerView = findViewById(R.id.listmusics_recyclerview);


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED){
            File root = Environment.getExternalStorageDirectory();
            musicsFiles = new ArrayList<>();
            musicsFiles.addAll(loadMusics2(root));
            musics = new ArrayList<>();
            extractMusicInfo(musics);
            displayMusics();
            Log.println(Log.DEBUG, "3131313131", String.valueOf(musicsFiles.size()));
        }
        else if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //explain user
            new AlertDialog.Builder(this).setTitle("Permission").setMessage("Application need to access" +
                    " storage to show musics on your device.").setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
                }
            }).setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ListMusicsActivity.this, "Permission not granted. Application will close...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finishAndRemoveTask();
                }
            }).show();
        }
        else{
            Toast.makeText(this, "Permission not granted. Application will close...", Toast.LENGTH_SHORT).show();
            finishAndRemoveTask();
        }

        //todo add bottom selection view
    }

    private ArrayList<File> loadMusics2(File root1){
    ArrayList<File> fileList = new ArrayList<>();
        File[] files = root1.listFiles();
        for(File file : files){
            if(file.isDirectory() && !file.isHidden()){
                fileList.addAll(loadMusics2(file));
            }
            else if(file.getName().endsWith(".mp3") || file.getName().endsWith(".MP3")){
                fileList.add(file);
            }
        }
        return fileList;
    }

    private void extractMusicInfo(ArrayList<AudioModel> musics){
        byte[] rawArt;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        AudioModel music;
        for(File f : musicsFiles){
            mediaMetadataRetriever.setDataSource(f.getAbsolutePath());
            long durationMs = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            String seconds = String.valueOf((durationMs % 60000) / 1000);
            String minutes = String.valueOf(durationMs / 60000);

            rawArt = mediaMetadataRetriever.getEmbeddedPicture();
            if(rawArt != null){
                 music = new AudioModel(f.getAbsolutePath(), minutes + ":" + seconds,
                        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                        rawArt);
            }
            else{
                 music = new AudioModel(f.getAbsolutePath(), minutes + ":" + seconds,
                        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            }

            musics.add(music);
        }
    }

    private void displayMusics(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(musics);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}