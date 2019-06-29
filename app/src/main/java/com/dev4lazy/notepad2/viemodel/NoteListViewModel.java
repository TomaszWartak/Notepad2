package com.dev4lazy.notepad2.viemodel;

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
    private final NotepadRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Note>> mObservableNotes;

    public NoteListViewModel(Application application) {
        super(application);

        mObservableNotes = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableNotes.setValue(null);

        mRepository = ((BasicApp) application).getRepository();
        LiveData<List<Note>> products = mRepository.getNotes();

        // observe the changes of the products from the database and forward them
        // todo poniżej 3 wersje kodu, pierwsza dziaa w Java 7, pozostale 8
        mObservableNotes.addSource(products, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        mObservableNotes.setValue(notes);
                    }
        });
        // mObservableNotes.addSource(products, notes -> mObservableNotes.setValue(notes));
        // mObservableNotes.addSource(products, mObservableNotes::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<Note>> getNotes() {
        return mObservableNotes;
    }

    public LiveData<List<Note>> searchNotes(String query) {
        return mRepository.loadNotes(query);
    }
}

