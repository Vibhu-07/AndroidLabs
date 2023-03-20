package com.cst2335.sikk0006;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cst2335.sikk0006.ChatMessage;
import com.cst2335.sikk0006.ChatMessageDAO;

@Database(entities = {ChatMessage.class}, version=1)
public abstract class MessageDatabase  extends RoomDatabase {
    public abstract ChatMessageDAO chatMessageDAO();

    public static final String DB_NAME = "chat_messages_db";

    private static MessageDatabase mInstance;
    public static synchronized MessageDatabase getInstance(Context ctx) {
        if(mInstance == null) {
            mInstance = Room.databaseBuilder(ctx.getApplicationContext(),
                            MessageDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return mInstance;
    }



}