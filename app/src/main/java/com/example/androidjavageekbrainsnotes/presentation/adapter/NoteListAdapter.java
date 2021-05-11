package com.example.androidjavageekbrainsnotes.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavageekbrainsnotes.R;
import com.example.androidjavageekbrainsnotes.domain.model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> {
    private final List<Note> list = new ArrayList<>();
    private final Fragment fragment;
    private int longClickedPosition = -1;

    public NoteListAdapter(Fragment fragment, OnNoteClicked clickListener) {
        this.fragment = fragment;
        this.clickListener = clickListener;
    }

    private final OnNoteClicked clickListener;

    public void setData(List<Note> toAdd) {
        NotesDiffUtilCallback callback = new NotesDiffUtilCallback(list, toAdd);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        list.clear();
        list.addAll(toAdd);

        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        Note note = list.get(position);
        holder.bind(note);
    }

    public int getLongClickedPosition() {
        return longClickedPosition;
    }

    public Note getItemAt(int longClickedPosition) {
        return list.get(longClickedPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public OnNoteClicked getClickListener() {
        return clickListener;
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView text;
        TextView date;
        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);

        public NoteListViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(v -> {
                if (getClickListener() != null) {
                    getClickListener().onNoteClicked(list.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(v -> {
                itemView.showContextMenu();
                longClickedPosition = getAdapterPosition();
                return true;
            });

            title = itemView.findViewById(R.id.title_note);
            text = itemView.findViewById(R.id.text_note);
            date = itemView.findViewById(R.id.date_note);
        }

        public void bind(Note note) {
            title.setText(note.getTitle());
            text.setText(note.getText());
            date.setText(sdf.format(note.getDate()));
        }
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public static class NotesDiffUtilCallback extends DiffUtil.Callback {
        private final List<Note> oldList;
        private final List<Note> newList;

        public NotesDiffUtilCallback(List<Note> oldList, List<Note> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        public List<Note> getOldList() {
            return oldList;
        }

        public List<Note> getNewList() {
            return newList;
        }
    }
}
