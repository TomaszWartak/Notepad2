package com.dev4lazy.notepad2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dev4lazy.notepad2.R;
import com.dev4lazy.notepad2.data.Note;
import com.dev4lazy.notepad2.utils.NoteKind;

import java.util.List;
//import java.util.Objects;

/*public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {*/
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    List<? extends Note> mNoteList;         

    /* todo to poniżej jest chyba na potrzrby databindingu
    @Nullable
    private final NoteClickCallback mNoteClickCallback;

    public NoteAdapter(@Nullable NoteClickCallback clickCallback) {
        mNoteClickCallback = clickCallback;
        setHasStableIds(true);
    }
    */

    private NoteClickCallback mNoteClickCallback = null;

    public NoteAdapter(NoteClickCallback noteClickCallback) {
        mNoteClickCallback = noteClickCallback;
        setHasStableIds(true);
    }
    public NoteAdapter(List<Note> noteList) {
        mNoteList = noteList;
        setHasStableIds(true);
    }

    public void setNoteList(final List<? extends Note> noteList) {
        if (mNoteList == null) {
            mNoteList = noteList;
            notifyItemRangeInserted(0, noteList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override          
                public int getOldListSize() {
                    return mNoteList.size();
                }

                @Override
                public int getNewListSize() {
                    return noteList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mNoteList.get(oldItemPosition).getId() ==
                            noteList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Note newNote = noteList.get(newItemPosition);
                    Note oldNote = mNoteList.get(oldItemPosition);
                    return newNote.equals(oldNote);
                    /*return newNote.getId() == oldNote.getId()
                            && Objects.equals(newNote.getDescription(), oldNote.getDescription())
                            && Objects.equals(newNote.getName(), oldNote.getName())
                            && newNote.getPrice() == oldNote.getPrice(); */
                }
            });
            mNoteList = noteList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        NoteItemBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.Note_item,
            parent, false);
        binding.setCallback(mNoteClickCallback);
        return new NoteViewHolder(binding);     $*/
        View view = LayoutInflater.from(
            //parent.getContext()).inflate(R.layout.note_list_item_idonly, parent, false);
                parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        /*
        holder.binding.setNote(mNoteList.get(position));
        holder.binding.executePendingBindings();
        */
       // Note note = mNoteList.get(position);
        holder.note = mNoteList.get(position);
        /* TextView noteIdTextView = holder.noteIdTextView;
        noteIdTextView.setText(String.valueOf(note.getId())); */
        TextView noteTitle = holder.noteTitleTextView;
        noteTitle.setText(holder.note.getTitle());
        TextView notePriority = holder.notePriorityTextView;
        notePriority.setText(String.valueOf(holder.note.getPriority()));
        TextView noteKind = holder.noteKindTextView;
        noteKind.setText(holder.note.getKind().getDescription());
        TextView noteCreationDate = holder.noteCreationDate;
        noteCreationDate.setText(holder.note.getCreationDate().toString());
    }

    @Override
    public int getItemCount() {
        return mNoteList == null ? 0 : mNoteList.size();
    }

    @Override
    public long getItemId(int position) {
        return mNoteList.get(position).getId();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

//        final NoteItemBinding binding;

 /*       public NoteViewHolder(NoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
       */
        //TextView noteIdTextView;
        Note note;
        TextView noteTitleTextView;
        TextView notePriorityTextView;
        TextView noteKindTextView;
        TextView noteCreationDate;
        
        NoteViewHolder(View itemView) {
            super(itemView);
            //noteIdTextView = itemView.findViewById(R.id.note_id);
            noteTitleTextView = itemView.findViewById(R.id.noteTitle);
            notePriorityTextView = itemView.findViewById(R.id.notePriority);
            noteKindTextView = itemView.findViewById(R.id.noteKind);
            noteCreationDate = itemView.findViewById(R.id.noteCreationDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNoteClickCallback.onNoteClick(note);
                }
            });

        }

    }
}