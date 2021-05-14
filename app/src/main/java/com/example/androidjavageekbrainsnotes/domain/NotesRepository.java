package com.example.androidjavageekbrainsnotes.domain;

import com.example.androidjavageekbrainsnotes.R;

import java.util.ArrayList;
import java.util.Date;

public class NotesRepository {

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(R.string.note_1_title, R.string.note_1_text, new Date()));
        notes.add(new Note(R.string.note_2_title, R.string.note_2_text, new Date()));
        notes.add(new Note(R.string.note_3_title, R.string.note_3_text, new Date()));
        notes.add(new Note(R.string.note_4_title, R.string.note_4_text, new Date()));
        notes.add(new Note(R.string.note_5_title, R.string.note_5_text, new Date()));
        return notes;
    }
}
