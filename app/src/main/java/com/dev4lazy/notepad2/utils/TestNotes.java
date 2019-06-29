package com.dev4lazy.notepad2.utils;

import com.dev4lazy.notepad2.data.Note;
import java.util.ArrayList;
import java.util.List;

public class TestNotes {

    private ArrayList<Note> notes;

    public TestNotes() {
        notes = new ArrayList<Note>();
        createNotes();
    }

    private void createNotes() {
        notes.add(
            new Note.NoteBuilder()
                .priority(0)
                .creationDate(System.currentTimeMillis())
                .title("Auchan")
                .content("spodnie, buty, krawat")
                .kind(NoteKind.SHOPPING)
                .build()
        );
        notes.add(
            new Note.NoteBuilder()
                    .priority(1)
                    .creationDate(System.currentTimeMillis())
                    .title("BRW")
                    .content("komoda")
                    .kind(NoteKind.SHOPPING)
                    .build()
        );
        notes.add(
            new Note.NoteBuilder()
                    .priority(0)
                    .creationDate(System.currentTimeMillis())
                    .title("PWR")
                    .content("zjazd")
                    .kind(NoteKind.PLACE)
                    .build()
        );
        notes.add(
            new Note.NoteBuilder()
                    .priority(0)
                    .creationDate(System.currentTimeMillis())
                    .title("Skoda")
                    .content("Codiac")
                    .kind(NoteKind.SHOPPING)
                    .build()
        );
        notes.add(
            new Note.NoteBuilder()
                    .priority(1)
                    .creationDate(System.currentTimeMillis())
                    .title("Brzeziński")
                    .content("Rozpoczęcie sezonu")
                    .kind(NoteKind.PERSON)
                    .build()
        );
        notes.add(
            new Note.NoteBuilder()
                    .priority(0)
                    .creationDate(System.currentTimeMillis())
                    .title("Kino Muza")
                    .content("MEGAPOLIS")
                    .kind(NoteKind.PLACE)
                    .build()
        );
        notes.add(
            new Note.NoteBuilder()
                    .priority(1)
                    .creationDate(System.currentTimeMillis())
                    .title("Pasibus")
                    .content("")
                    .kind(NoteKind.PLACE)
                    .build()
        );

    }

    public List<Note> getNotes() {
        return notes;
    }

    public Note getNote() { return getNotes().get(0);}
}
