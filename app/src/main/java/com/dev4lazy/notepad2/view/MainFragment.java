package com.dev4lazy.notepad2.view;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dev4lazy.notepad2.R;
import com.dev4lazy.notepad2.data.Note;
import com.dev4lazy.notepad2.utils.NoteKind;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class MainFragment extends Fragment implements NoteClickCallback {

    private NoteListViewModel mViewModel;
    private NoteAdapter mNoteAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerSetup();
        floatingActionButtonSetup();
        mViewModel = ViewModelProviders.of(this).get(NoteListViewModel.class);
        //mNoteAdapter.setNoteList(mViewModel.getNotes().getValue());
        mViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> noteList) {
                if (!noteList.isEmpty()) {
                    mNoteAdapter.setNoteList(noteList);
                }
            }
        });
    }

    private void recyclerSetup() {
        mNoteAdapter = new NoteAdapter(this);
        recyclerView = getView().findViewById(R.id.notes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // todo ????
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        recyclerView.setAdapter(mNoteAdapter);
    }

    private void floatingActionButtonSetup() {
        floatingActionButton = getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
    }

    private void addNote() {
        //todo a czy tu nie jest potrzebny viewmodel?
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Resources resources = getResources();

        builder.setTitle(resources.getString(R.string.header_new_note));
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.edit_note, null);

        final EditText noteTitleInput = (EditText) viewInflated.findViewById(R.id.editNoteTitle);
        final EditText notePriorityInput = (EditText) viewInflated.findViewById(R.id.editNotePriority);
        final EditText noteKindInput = (EditText) viewInflated.findViewById(R.id.editNoteKind);
        final EditText noteContentInput = (EditText) viewInflated.findViewById(R.id.editNoteContent);

        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                String title = noteTitleInput.getText().toString();
                int priority = Integer.parseInt(notePriorityInput.getText().toString());
                NoteKind noteKind = NoteKind.values()[Integer.parseInt(noteKindInput.getText().toString())];
                String content = noteContentInput.getText().toString();
                Note note = new Note.NoteBuilder()
                        .priority(priority)
                        .creationDate(System.currentTimeMillis())
                        .title(title)
                        .content(content)
                        .kind(noteKind)
                        .build();
                mViewModel.insertNote(note);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onNoteClick(Note note) {
        editNote( note );
    }

    private void editNote(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Resources resources = getResources();

        builder.setTitle(resources.getString(R.string.header_edit_note));
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.edit_note, null);

        final EditText noteTitleInput = (EditText) viewInflated.findViewById(R.id.editNoteTitle);
        noteTitleInput.setText(note.getTitle());
        final EditText notePriorityInput = (EditText) viewInflated.findViewById(R.id.editNotePriority);
        notePriorityInput.setText(String.valueOf(note.getPriority()));
        final EditText noteKindInput = (EditText) viewInflated.findViewById(R.id.editNoteKind);
        noteKindInput.setText(Integer.toString(note.getKind().ordinal()));
        final EditText noteContentInput = (EditText) viewInflated.findViewById(R.id.editNoteContent);
        noteContentInput.setText(note.getContent());

        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                String title = noteTitleInput.getText().toString();
                int priority = Integer.parseInt(notePriorityInput.getText().toString());
                NoteKind noteKind = NoteKind.values()[Integer.parseInt(noteKindInput.getText().toString())];
                String content = noteContentInput.getText().toString();

                mViewModel.insertNote(new Note.NoteBuilder()
                        .id(note.getId())
                        .priority(priority)
                        .creationDate(System.currentTimeMillis())
                        .title(title)
                        .content(content)
                        .kind(noteKind)
                        .build());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
