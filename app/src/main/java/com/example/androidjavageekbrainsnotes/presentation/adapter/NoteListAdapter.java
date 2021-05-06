package com.example.androidjavageekbrainsnotes.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    public NoteListAdapter(OnNoteClicked clickListener) {
        this.clickListener = clickListener;
    }

    private final OnNoteClicked clickListener;

    public void addData(List<Note> toAdd) {
        list.clear();
        list.addAll(toAdd);
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

            itemView.setOnClickListener(v -> {
                if (getClickListener() != null) {
                    getClickListener().onNoteClicked(list.get(getAdapterPosition()));
                }
            });

            title = itemView.findViewById(R.id.title_note);
            text = itemView.findViewById(R.id.text_note);
            date = itemView.findViewById(R.id.date_note);
        }

        public void bind(Note note) {
            title.setText(note.getTitleRes());
            text.setText(note.getTextRes());
            date.setText(sdf.format(note.getDate()));
        }
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }
}
