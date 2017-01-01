package com.viveksb007.otakuworld;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by viveksb007 on 13/12/16.
 */

public class ResponseObject {

    private String id, title, sysnopsis, youtube_id;
    private Uri cover_uri;
    private String[] genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSysnopsis() {
        return sysnopsis;
    }

    public void setSysnopsis(String sysnopsis) {
        this.sysnopsis = sysnopsis;
    }


    public Uri getCover_uri() {
        return cover_uri;
    }

    public void setCover_uri(Uri cover_uri) {
        this.cover_uri = cover_uri;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }
}
