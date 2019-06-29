package com.dev4lazy.notepad2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes WHERE id= :id")
    LiveData<List<Note>> findNoteById(String id);

    //todo findNoteByName like

    @Query("SELECT * FROM notes ORDER BY title ASC")
    LiveData<List<Note>> getNotesByTitle();

    @Query("SELECT * FROM notes ORDER BY priority ASC, creation_date DESC")
    LiveData<List<Note>> getNotesByPriorityAndCreationDate();

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    // todo to jest na potrzeby testów
    @Query("SELECT * FROM notes")
    List<Note> getAllNotesList();

    //todo findNotesByWholaTextSearch
    @RawQuery(observedEntities = Note.class)
    LiveData<List<Note>>getNotesViaQuery(SupportSQLiteQuery query) ;
}