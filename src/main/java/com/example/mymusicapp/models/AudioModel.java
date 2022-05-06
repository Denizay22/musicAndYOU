package com.example.mymusicapp.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class AudioModel implements Serializable {

    private String path;
    private String duration;
    private String name;
    private String artist;
    private byte[] art;

    public AudioModel(String path, String duration, String name, String artist, byte[] art) {
        this.path = path;
        this.duration = duration;
        this.name = name;
        this.artist = artist;;
        this.art = art;
    }

    public AudioModel(String path, String duration, String name, String artist) {
        this.path = path;
        this.duration = duration;
        this.name = name;
        this.artist = artist;
        this.art = null;
    }

    public String getPath() {
        return path;
    }

    public String getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public byte[] getArt() {
        return art;
    }

}