package com.example.androidjavageekbrainsnotes.presentation.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidjavageekbrainsnotes.domain.model.Note;
import com.example.androidjavageekbrainsnotes.domain.repository.Callback;
import com.example.androidjavageekbrainsnotes.domain.repository.NotesRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class NoteListViewModel extends ViewModel {
    private final NotesRepositoryImpl notesRepositoryImpl = new NotesRepositoryImpl();
    private final MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public LiveData<Note> getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note note) {
        selectedNote.setValue(note);
    }

    public void requestNotes() {
        notesRepositoryImpl.getNotes(new Callback<ArrayList<Note>>() {


            @Override
            public void onSuccess(ArrayList<Note> value) {
                notesLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void addNote(String title, String text) {
        notesRepositoryImpl.addNote(title, text, new Callback<Note>() {
            @Override
            public void onSuccess(Note value) {
                if (notesLiveData.getValue() != null) {

                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());

                    notes.add(value);

                    notesLiveData.setValue(notes);
                } else {
                    ArrayList<Note> notes = new ArrayList<>();
                    notes.add(value);

                    notesLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void updateNote(Note note) {
        notesRepositoryImpl.updateNote(note, new Callback<Note>() {

            @Override
            public void onSuccess(Note value) {
                if (notesLiveData.getValue() != null) {

                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());

                    int index = notes.indexOf(value);

                    if (index != -1) {
                        notes.set(index, value);
                    }

                    notesLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void deleteNote(Note note) {
        notesRepositoryImpl.deleteNote(note, new Callback<Object>() {

            @Override
            public void onSuccess(Object value) {
                requestNotes();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
