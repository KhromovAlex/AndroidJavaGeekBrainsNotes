package com.example.androidjavageekbrainsnotes.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidjavageekbrainsnotes.R;
import com.example.androidjavageekbrainsnotes.presentation.viewModel.NoteListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NoteCreateFragment extends Fragment {
    private TextInputEditText titleNewNote;
    private TextInputEditText textNewNote;
    private NoteListViewModel viewModel;
    private NavController navController;

    public NoteCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NoteListViewModel.class);
        navController = Navigation.findNavController(view);
        MaterialButton buttonAddNote = view.findViewById(R.id.note_create__button_add);
        titleNewNote = view.findViewById(R.id.note_create__title);
        textNewNote = view.findViewById(R.id.note_create__text);
        buttonAddNote.setOnClickListener(v -> {
            String title = Objects.requireNonNull(titleNewNote.getText()).toString().trim();
            String text = Objects.requireNonNull(textNewNote.getText()).toString().trim();

            if (title.equals("") || text.equals("")) return;

            viewModel.addNote(title, text);
            navController.popBackStack();
        });
    }
}
