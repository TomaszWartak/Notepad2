package com.dev4lazy.notepad2.data;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

public class NotepadRepository {

    private static NotepadRepository sInstance;
    private NoteDao noteDao;
    private MediatorLiveData<List<Note>> mObservableNotes;

    private NotepadRepository(final NotepadDatabase database) {
        noteDao = database.noteDao();
        mObservableNotes = new MediatorLiveData<>();
        // todo start test
       // List<Note> testNotes = noteDao.getAllNotes().getValue();
        //testNotes = noteDao.getAllNotesList();
        // todo end test
        mObservableNotes.addSource(
                noteDao.getAllNotes(),
                new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                         if (database.getDatabaseCreated().getValue() != null) {
                             mObservableNotes.postValue(notes);
                         }
                    }
                });
    }

    public static NotepadRepository getInstance(final NotepadDatabase database) {
        if (sInstance == null) {
            synchronized (NotepadRepository.class) {
                if (sInstance == null) {
                    sInstance = new NotepadRepository(database);
                }
            }
        }
        return sInstance;
    }

    public void insertNote( Note note ) {
       // noteDao.insert(note);
        insert(note);
    }

    public void updateNote( Note note ) {
        noteDao.update(note);
    }

    public void deleteNote( Note note ) {
        noteDao.delete(note);
    }

    public LiveData<List<Note>> loadNote(final int noteId) {
        return noteDao.findNoteById(String.valueOf(noteId));
    }
    public LiveData<List<Note>> getNotes() {
        return mObservableNotes;
    }

    public LiveData<List<Note>> loadNotes(String filter) {
        if ((filter!=null) && (!filter.isEmpty())) {
            return noteDao.getNotesViaQuery(new SimpleSQLiteQuery(filter));
        }
        return noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    private static class InsertAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteDao mAssyncTaskDAO;

        InsertAsyncTask(NoteDao dao) {
            mAssyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAssyncTaskDAO.insert(params[0]);
            return null;
        }
    }


}
