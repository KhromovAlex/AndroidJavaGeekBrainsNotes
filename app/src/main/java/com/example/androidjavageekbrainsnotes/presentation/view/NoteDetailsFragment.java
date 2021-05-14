package com.example.androidjavageekbrainsnotes.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidjavageekbrainsnotes.R;
import com.example.androidjavageekbrainsnotes.domain.model.Note;
import com.example.androidjavageekbrainsnotes.presentation.viewModel.NoteListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteDetailsFragment extends Fragment {
    private NoteListViewModel viewModel;
    private TextView title;
    private TextView text;
    private TextView date;

    public NoteDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(NoteListViewModel.class);
        title = view.findViewById(R.id.title_note);
        text = view.findViewById(R.id.text_note);
        date = view.findViewById(R.id.date_note);

        viewModel.getSelectedNote().observe(getViewLifecycleOwner(), this::updateUi);
    }

    private void updateUi(Note selectedNote) {
        title.setText(selectedNote.getTitleRes());

        text.setText(selectedNote.getTextRes());

        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        date.setText(sdf.format(selectedNote.getDate()));
    }
}
