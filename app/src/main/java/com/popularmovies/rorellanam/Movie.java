package com.popularmovies.rorellanam;

import java.io.Serializable;

/**
 * Created by Rorellanam on 9/4/16.
 */
public class Movie implements Serializable {

    private String title;
    private String imagePoster;
    private String sipnosis;
    private String date;

    public Movie(String title, String imagePoster, String sipnosis, String date) {
        this.title = title;
        this.imagePoster = imagePoster;
        this.sipnosis = sipnosis;
        this.date = date;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", imagePoster='" + imagePoster + '\'' +
                ", sipnosis='" + sipnosis + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    //Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    public void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    public String getSipnosis() {
        return sipnosis;
    }

    public void setSipnosis(String sipnosis) {
        this.sipnosis = sipnosis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
