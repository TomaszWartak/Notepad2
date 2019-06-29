package com.dev4lazy.notepad2.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteKindConverterTest {

    NoteKindConverter noteKindConverter;
    @Before
    public void setUp() throws Exception {
        noteKindConverter = new NoteKindConverter();
    }

    @Test
    public void int2NoteKind() {
        assertEquals(NoteKind.SHOPPING, noteKindConverter.int2NoteKind(0));
        assertEquals(NoteKind.PERSON, noteKindConverter.int2NoteKind(1));
        assertEquals(NoteKind.PLACE, noteKindConverter.int2NoteKind(2));
    }

    @Test
    public void noteKind2Int() {
        assertEquals(0, noteKindConverter.noteKind2Int(NoteKind.SHOPPING));
        assertEquals( 1, noteKindConverter.noteKind2Int( NoteKind.PERSON));
        assertEquals( 2, noteKindConverter.noteKind2Int( NoteKind.PLACE));
    }
}