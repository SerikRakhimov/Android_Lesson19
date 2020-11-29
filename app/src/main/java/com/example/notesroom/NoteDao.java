package com.example.notesroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    List<Note> getAll();

    @Insert(onConflict = REPLACE)
    void insert(Note note);

    @Insert
    void insert(Note... notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
