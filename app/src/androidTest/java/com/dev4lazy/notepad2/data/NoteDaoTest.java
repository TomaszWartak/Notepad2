package com.dev4lazy.notepad2.data;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.dev4lazy.notepad2.utils.LiveDataTestUtil;
import com.dev4lazy.notepad2.utils.NoteKind;
import com.dev4lazy.notepad2.utils.TestNotes;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NoteDaoTest {
    // todo wywala się przy grupowym teście bo wyjątki się "gonią" i zamykają sobie wzajemnie database
    // pojedyncze testy działają i to jest najważniejsze
    // lol przestały działać, jak zrobilem callCounter

    static NotepadDatabase database;
    private NoteDao noteDao;
    /* todo to trzeba synchronizować czy coś..
        callCounter - na potrzeby liczenia ile jest uruchomionych tesow z wątkami.
        Służy do sprawdzenia, czy można już zamknąć bazę.
        Bez tego, w przypadku uruchomienia wszystkich testów, może się zdarzyć,
        że jeden wątek zamknie bazę innemu wątkowi i test kończy się niepowodzeniem
        ("android test Cannot perform this operation because the connection pool has been closed.")
     */
    int callCounter = 0;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        database = NotepadDatabase.getInstance(context);
        //database = Room.inMemoryDatabaseBuilder(context,NotepadDatabase.class).build();
        noteDao = database.noteDao();
        noteDao.deleteAll();
        addTestNotes();
        callCounter++;
    }

    private void addTestNotes() {
        TestNotes testNotes = new TestNotes();
        for (Note note : testNotes.getNotes()) {
            noteDao.insert(note);
        }
        List<Note> list = noteDao.getAllNotesList();
    }

    @Test
    public void insertNote() throws InterruptedException{
        //noteDao.deleteAll();
        Note note = new Note.NoteBuilder()
                .priority(0)
                .creationDate(System.currentTimeMillis())
                .title("Auchan")
                .content("spodnie, buty, krawat")
                .kind(NoteKind.SHOPPING)
                .build();
        noteDao.insert(note);
        List<Note> notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        assertEquals(8, notes.size());
    }

    @Test
    public void updateNote() throws InterruptedException{
        noteDao.deleteAll();
        Note note = new Note.NoteBuilder()
                .priority(0)
                .creationDate(System.currentTimeMillis())
                .title("Bauhaus")
                .content("beton komórkowy")
                .kind(NoteKind.SHOPPING)
                .build();
        noteDao.insert(note);
        List<Note> notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        note = notes.get(0);
        int noteId = note.getId();
        note.setTitle("Castorama");
        noteDao.update(note);
        notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        note = notes.get(0);
        assertEquals(noteId, note.getId());
        assertEquals("Castorama", note.getTitle());
    }

    @Test
    public void deleteNote() throws InterruptedException{
        noteDao.deleteAll();
        Note note = new Note.NoteBuilder()
                .priority(0)
                .creationDate(System.currentTimeMillis())
                .title("Leroy merlin")
                .content("beton komórkowy")
                .kind(NoteKind.SHOPPING)
                .build();
        noteDao.insert(note);
        List<Note> notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        note = notes.get(0);
        noteDao.delete(note);
        notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        assertEquals(0, notes.size());
    }

    @Test
    public void getAllNotes() throws InterruptedException{
        List<Note> notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        assertEquals(7, notes.size());
    }

    @Test
    public void findNoteById() throws InterruptedException{
        noteDao.deleteAll();
        Note note = new Note.NoteBuilder()
                .priority(0)
                .creationDate(System.currentTimeMillis())
                .title("OBI")
                .content("spodnie, buty, krawat")
                .kind(NoteKind.SHOPPING)
                .build();
        noteDao.insert(note);
        List<Note> notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        note = notes.get(0);
        int noteId = note.getId();
        note.setTitle("Leroy Merlin");
        notes = LiveDataTestUtil.getValue(noteDao.findNoteById(String.valueOf(noteId)));
        assertEquals(noteId, notes.get(0).getId());
        assertEquals("OBI", notes.get(0).getTitle());
    }

    @After
    public void tearDown() throws Exception {
        callCounter--;
        if (callCounter<1)
            database.close();
    }


}