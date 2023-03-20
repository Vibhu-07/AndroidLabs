package com.cst2335.sikk0006;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    // To insert chatMessage in array
    @Insert
    public void insertMessage(ChatMessage message);

    // Get all ChatMessages from the database
    @Query("SELECT * FROM ChatMessage")
    public List<ChatMessage> getAllMessages();

    // Delete function
    @Delete
    void deleteMessage(ChatMessage m);

}
