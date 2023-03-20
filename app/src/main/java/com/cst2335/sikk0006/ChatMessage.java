package com.cst2335.sikk0006;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class ChatMessage  {
    //adding columns
    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo (name= "TimeSent")
    protected String timeSent;
    @ColumnInfo (name="SendOrRecieve")
    protected int SendOrRecieve;

    private boolean isSentButton;

//    ArrayList<ChatMessage> messages;

    //creating primary key and Id
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo (name="id")
    public int id;

    public ChatMessage(String message, String timeSent, boolean isSentButton) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }
}
