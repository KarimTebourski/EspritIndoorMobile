package com.example.espritindoor.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment {
    @SerializedName("_id")
    @Expose
    private String _id ;
    private String sender ;
    private  String username;
    private String contenu ;
    @SerializedName("createdAt")
    @Expose
    private Date timestamps ;

    public Comment(String _id, String sender,String username ,String contenu, Date timestamps) {
        this._id = _id;
        this.sender = sender;
        this.contenu = contenu;
        this.username = username;
        this.timestamps = timestamps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Date timestamps) {
        this.timestamps = timestamps;
    }
}
