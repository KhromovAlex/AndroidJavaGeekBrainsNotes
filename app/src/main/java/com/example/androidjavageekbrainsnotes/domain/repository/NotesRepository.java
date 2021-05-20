package com.example.androidjavageekbrainsnotes.domain.repository;

import com.example.androidjavageekbrainsnotes.domain.model.Note;

import java.util.ArrayList;

public interface NotesRepository {
    void getNotes(Callback<ArrayList<Note>> callback);

    void addNote(String title, String text, Callback<Note> callback);

    void updateNote(Note note, Callback<Note> callback);

    void deleteNote(Note item, Callback<Object> callback);
}
