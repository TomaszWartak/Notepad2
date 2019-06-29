package com.dev4lazy.notepad2.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.dev4lazy.notepad2.data.Note;
import com.dev4lazy.notepad2.data.NotepadRepository;
import com.dev4lazy.notepad2.utils.BasicApp;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel {
    private NotepadRepository notepadRepository;
    private final MediatorLiveData<List<Note>> mObservableNotes;

    public NoteListViewModel(Application application) {
        super(application);
        mObservableNotes = new MediatorLiveData<>();
        notepadRepository = ((BasicApp) application).getRepository();
        mObservableNotes.addSource(notepadRepository.getNotes(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> noteList) {
                mObservableNotes.setValue(noteList);
            }
        });
        //mObservableNotes.addSource(notepadRepository.getNotes(), mObservableProducts::setValue);
    }

    public void insertNote(Note note) {
        notepadRepository.insertNote(note);
    }

    public void updateNote(Note note) {
        notepadRepository.updateNote(note);
    }

    public void deleteNote(Note note) {
        notepadRepository.deleteNote(note);
    }

    public LiveData<List<Note>> getNotes() {
        return mObservableNotes;
    }

    public LiveData<List<Note>> searchNotes(String query) {
        return notepadRepository.loadNotes(query);
    }
}
