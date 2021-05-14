package com.example.androidjavageekbrainsnotes.domain.repository;

import com.example.androidjavageekbrainsnotes.domain.model.Note;

import java.util.ArrayList;

public class NotesRepository {
    private final ArrayList<Note> data = new ArrayList<>();

    public ArrayList<Note> getNotes() {
        return data;
    }

    public void addNote(Note note) {
        data.add(note);
    }

    public void updateNote(Note note) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(note.getId())) {
                data.set(i, note);
            }
        }
    }

    public void deleteNote(int index) {
        data.remove(index);
    }
}
