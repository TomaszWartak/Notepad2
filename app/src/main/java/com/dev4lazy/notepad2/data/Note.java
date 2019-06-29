package com.dev4lazy.notepad2.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.dev4lazy.notepad2.utils.DateConverter;
import com.dev4lazy.notepad2.utils.NoteKind;
import com.dev4lazy.notepad2.utils.NoteKindConverter;

import java.util.Date;
import java.util.Objects;

@Entity(
    tableName = "notes",
    indices = {
        @Index(name = "index_id_date", value = {"id", "creation_date"})}
    )
@TypeConverters({
        DateConverter.class,
        NoteKindConverter.class
    })
/*
Historia wersji:
2:
    - pole creationDate zmienione z int na Date
    - pole kind zmienione z int na NoteKind

 */
public class Note {

    /* Jeśli dodasz kolejne pole, to uwzględnij to w:
        - w historii wersji
        - dodaj migrację w NoteDatabase
        - equals
        - konstruktorze
        - builderze
        - ???
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int priority;
    @ColumnInfo(name = "creation_date")
    private Date creationDate;
    private String title;
    private String content;
    private NoteKind kind;

    public Note() {

    }

    public Note(int priority, Date date, String title, String content, NoteKind kind) {
        this.priority = priority;
        this.creationDate = date;
        this.title = title;
        this.content = content;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = new Date(creationDate);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteKind getKind() {
        return kind;
    }

    public void setKind(NoteKind kind) {
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                priority == note.priority &&
                kind == note.kind &&
                Objects.equals(creationDate, note.creationDate) &&
                title.equals(note.title) &&
                Objects.equals(content, note.content);
    }

    @Override
    public int hashCode() {
        return 0;
    }


    //------------------- NoteBuilder
    public static class NoteBuilder {

        private int id;
        private int priority;
        private Date creationDate;
        private String title;
        private String content;
        private NoteKind kind;

        public NoteBuilder id(int id) {
            this.id = id;
            return this;
        }
        public NoteBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public NoteBuilder creationDate(long date) {
            this.creationDate = new Date(new Long(date));
            return this;
        }

        public NoteBuilder creationDate(Date date) {
            this.creationDate = date;
            return this;
        }

        public NoteBuilder title(String title) {
            String result = "";
            if ((title!=null)&&(!title.isEmpty())) {
                result = title;
            }
            this.title = result;
            return this;
        }

        public NoteBuilder content(String content) {
            String result = "";
            if ((content!=null)&&(!content.isEmpty())) {
                result = content;
            }
            this.content = result;
            return this;
        }

        /*
        public NoteBuilder kind(NoteKind kind) {
            this.kind = kind;
            return this;
        }
        */

        public NoteBuilder kind(NoteKind noteKind) {
            // todo jakieś sprawdzanie null itp
            this.kind = noteKind;
            return this;
        }

        public Note build() {
            return new Note(priority, creationDate,title, content, kind);
        }
    } 
}
