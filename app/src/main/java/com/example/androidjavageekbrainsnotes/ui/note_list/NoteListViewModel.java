package com.example.androidjavageekbrainsnotes.ui.note_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidjavageekbrainsnotes.domain.Note;
import com.example.androidjavageekbrainsnotes.domain.NotesRepository;

import java.util.ArrayList;

public class NoteListViewModel extends ViewModel {
    private final NotesRepository notesRepository = new NotesRepository();
    private final MutableLiveData<ArrayList<Note>> notes = new MutableLiveData<>();
    private final MutableLiveData<Note> selectedNote = new MutableLiveData<>();

    public NoteListViewModel() {
        notes.postValue(notesRepository.getNotes());
    }

    public LiveData<ArrayList<Note>> getNoteList() {
        return notes;
    }

    public LiveData<Note> getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note note) {
        selectedNote.postValue(note);
    }
}
