package com.dev4lazy.notepad2.utils;

import androidx.room.TypeConverter;

public class NoteKindConverter {
    @TypeConverter
    public NoteKind int2NoteKind(int kind) {
        return NoteKind.values()[kind];
    }

    @TypeConverter
    public int noteKind2Int(NoteKind kind) {
        return kind.ordinal();
    }
}
