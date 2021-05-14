package com.example.androidjavageekbrainsnotes.ui.note_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidjavageekbrainsnotes.R;
import com.example.androidjavageekbrainsnotes.domain.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteListFragment extends Fragment {
    private NoteListViewModel viewModel;
    private TextView title;
    private TextView text;
    private TextView date;

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private OnNoteClicked onNoteClicked;

    public NoteListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClicked) {
            onNoteClicked = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClicked = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NoteListViewModel.class);
        LinearLayout notesLayout = view.findViewById(R.id.notes_list);

        viewModel.getNoteList().observe(getViewLifecycleOwner(), list -> {
            for (Note note : list) {
                View noteView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesLayout, false);
                title = noteView.findViewById(R.id.title_note);
                text = noteView.findViewById(R.id.text_note);
                date = noteView.findViewById(R.id.date_note);
                noteView.setOnClickListener(v -> {
                    viewModel.setSelectedNote(note);
                    openNoteDetail(note);
                });

                updateUi(note);
                notesLayout.addView(noteView);
            }
        });
    }

    private void openNoteDetail(Note note) {
        if (onNoteClicked != null) {
            onNoteClicked.onNoteClicked(note);
        }
    }

    private void updateUi(Note note) {
        title.setText(note.getTitleRes());

        text.setText(note.getTextRes());

        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        date.setText(sdf.format(note.getDate()));
    }
}
