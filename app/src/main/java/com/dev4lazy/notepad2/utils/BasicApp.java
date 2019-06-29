package com.dev4lazy.notepad2.utils;

import android.app.Application;

import com.dev4lazy.notepad2.data.NotepadDatabase;
import com.dev4lazy.notepad2.data.NotepadRepository;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public NotepadDatabase getDatabase() {
        return NotepadDatabase.getInstance(this);
    }

    public NotepadRepository getRepository() {
        return NotepadRepository.getInstance(getDatabase());
    }
}

