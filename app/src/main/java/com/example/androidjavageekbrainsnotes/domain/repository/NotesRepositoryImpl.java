package com.example.androidjavageekbrainsnotes.domain.repository;

import com.example.androidjavageekbrainsnotes.domain.model.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NotesRepositoryImpl implements NotesRepository {
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String DATE = "date";
    private static final String NOTES = "notes";
    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<ArrayList<Note>> callback) {
        fireStore.collection(NOTES)
                .orderBy(DATE)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if (result == null) return;
                        ArrayList<Note> tmp = new ArrayList<>();
                        List<DocumentSnapshot> docs = result.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            String id = doc.getId();
                            String title = doc.getString(TITLE);
                            String text = doc.getString(TEXT);
                            Date date = doc.getDate(DATE);

                            tmp.add(new Note(id, title, text, date));
                        }
                        callback.onSuccess(tmp);
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    @Override
    public void addNote(String title, String text, Callback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();
        Date date = new Date();
        data.put(TITLE, title);
        data.put(TEXT, text);
        data.put(DATE, date);

        fireStore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference result = task.getResult();
                        if (result == null) return;
                        callback.onSuccess(new Note(result.getId(), title, text, date));
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    @Override
    public void updateNote(Note note, Callback<Object> callback) {
        fireStore.collection(NOTES)
                .document(note.getId())
                .update(
                        TITLE, note.getTitle(),
                        TEXT, note.getText(),
                        DATE, note.getDate()
                )
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(new Object());
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    @Override
    public void deleteNote(Note item, Callback<Object> callback) {
        fireStore.collection(NOTES)
                .document(item.getId())
                .delete()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess(new Object());
                    } else {
                        callback.onError(task.getException());
                    }

                });
    }
}
