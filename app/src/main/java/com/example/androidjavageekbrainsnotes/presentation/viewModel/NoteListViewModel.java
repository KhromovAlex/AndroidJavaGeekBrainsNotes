package com.example.androidjavageekbrainsnotes.presentation.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidjavageekbrainsnotes.domain.model.Note;
import com.example.androidjavageekbrainsnotes.domain.repository.NotesRepository;

import java.util.List;

public class NoteListViewModel extends ViewModel {
    private final NotesRepository notesRepository = new NotesRepository();
    private final MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public LiveData<Note> getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note note) {
        selectedNote.postValue(note);
    }

    public void requestNotes() {
        List<Note> notes = notesRepository.getNotes();
        notesLiveData.postValue(notes);
    }
}
