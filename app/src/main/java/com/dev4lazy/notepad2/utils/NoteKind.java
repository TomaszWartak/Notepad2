package com.dev4lazy.notepad2.utils;

public enum NoteKind {
    SHOPPING ("Zakupy"),
    PERSON ("Osoba"),
    PLACE ("Miejsce");

    private String description;

    NoteKind(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }

    // public final ordinal()- zwraca wartość int informującą o miejscu deklaracji danej stałej w wyliczeniu
}
