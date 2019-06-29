package com.dev4lazy.notepad2.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dev4lazy.notepad2.utils.AppExecutors;
import com.dev4lazy.notepad2.utils.NoteKind;
import com.dev4lazy.notepad2.utils.TestNotes;

import java.util.Date;

// Zob. historię wersji w klasie Note
@Database(entities = {Note.class}, version = 1)
public abstract class NotepadDatabase extends RoomDatabase {

    private final static String DATABASE_NAME = "notepad_database";
    private static volatile NotepadDatabase sInstance;
    public abstract NoteDao noteDao();
    // todo po co jest mIsDatabaseCreated
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static NotepadDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (NotepadDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                NotepadDatabase.class, DATABASE_NAME )
                            //.addCallback(roomDatabaseCallback)
                            //.addMigrations(MIGRATION_1_2)
                            .build();
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                 }
            }
        }
        return sInstance;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    /*
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // pomiedzy wersją 1 i 2 zmieniły się tylko typy pól -> zob historię wersji w Note
            // Create the new table
            database.execSQL(
                    "CREATE TABLE notes_new (" +
                            "id INTEGER, " +
                            "priority INTEGER, " +
                            "creationDate INTEGER, "+
                            "title TEXT, "+
                            "content TEXT, "+
                            "kind INTEGER NULL, "+
                            "PRIMARY KEY(id))");
            // Copy the data
            database.execSQL(
                    "INSERT INTO notes_new SELECT * FROM notes");
            // -------Remove the old table
            // ------ database.execSQL("DROP TABLE users");
            // Rename table
            database.execSQL("ALTER TABLE notes RENAME TO notes_old");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE notes_new RENAME TO notes");
            // Remove the old table
            database.execSQL("DROP TABLE notes_old");
        }
    };
*/

    //todo co z tym callbackiem...
    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(sInstance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final NoteDao dao;
        private final TestNotes testNotes;

        PopulateDbAsync(NotepadDatabase db) {
            dao = db.noteDao();
            testNotes = new TestNotes();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            dao.deleteAll();
            for (Note note : testNotes.getNotes()) {
                dao.insert(note);
            }
            return null;
        }
    }
}