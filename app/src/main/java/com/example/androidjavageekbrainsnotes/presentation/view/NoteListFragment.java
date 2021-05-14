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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavageekbrainsnotes.R;
import com.example.androidjavageekbrainsnotes.domain.model.Note;
import com.example.androidjavageekbrainsnotes.presentation.adapter.NoteListAdapter;
import com.example.androidjavageekbrainsnotes.presentation.viewModel.NoteListViewModel;

public class NoteListFragment extends Fragment {
    private NoteListViewModel viewModel;
    private NavController navController;
    private RecyclerView notesList;

    public NoteListFragment() {
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
        navController = Navigation.findNavController(view);

        notesList = view.findViewById(R.id.notes_list);
        NoteListAdapter noteListAdapter = new NoteListAdapter(this::openNote);

        if (savedInstanceState == null) {
            viewModel.requestNotes();
        }

        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), list -> {
            noteListAdapter.addData(list);
            noteListAdapter.notifyDataSetChanged();
        });

        notesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notesList.setAdapter(noteListAdapter);

    }

    public void openNote(Note note) {
        viewModel.setSelectedNote(note);
        navController.navigate(R.id.nav_note_details);
    }
}
