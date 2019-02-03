package com.example.spotif_ai;

/**
 * Created by paen3 on 2/3/2019.
 */

public class Song {
    String title, artist, emotion;
    int index;
    public Song(){

    }
    public Song(String title, String artist, String emotion, int index){
        this.artist = artist;
        this.title = title;
        this.emotion = emotion;
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getEmotion() {
        return emotion;
    }

    public int getIndex() {
        return index;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
