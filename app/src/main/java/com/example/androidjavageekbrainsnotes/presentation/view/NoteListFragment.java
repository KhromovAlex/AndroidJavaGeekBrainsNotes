package com.example.androidjavageekbrainsnotes.presentation.view;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteListFragment extends Fragment {
    private NoteListViewModel viewModel;
    private NavController navController;
    private NoteListAdapter noteListAdapter;

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

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        FloatingActionButton actionButtonAddNote = view.findViewById(R.id.button_add_note);
        actionButtonAddNote.setOnClickListener(v -> new NoteCreateFragment().show(getParentFragmentManager(), "NoteCreate"));
        noteListAdapter = new NoteListAdapter(this, this::openNote);

        viewModel.requestNotes();

        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), noteListAdapter::setData);

        notesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notesList.setAdapter(noteListAdapter);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_open) {
            openNote(noteListAdapter.getItemAt(noteListAdapter.getLongClickedPosition()));
            return true;
        }

        if (item.getItemId() == R.id.action_update) {
            updateNote(noteListAdapter.getItemAt(noteListAdapter.getLongClickedPosition()));
            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            showRemoveAlert();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void showRemoveAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder
                .setMessage(R.string.alert_remove_message)
                .setIcon(R.drawable.ic_baseline_delete_forever_24)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteNote(noteListAdapter.getItemAt(noteListAdapter.getLongClickedPosition())))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    public void openNote(Note note) {
        viewModel.setSelectedNote(note);
        navController.navigate(R.id.nav_note_details);
    }

    public void updateNote(Note note) {
        viewModel.setSelectedNote(note);
        new NoteEditFragment().show(getParentFragmentManager(), "NoteEdit");
    }

    public void deleteNote(Note note) {
        viewModel.deleteNote(note);
    }
}
