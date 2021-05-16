package com.example.androidjavageekbrainsnotes.presentation.view;

import android.app.DatePickerDialog;
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
import com.example.androidjavageekbrainsnotes.domain.model.Note;
import com.example.androidjavageekbrainsnotes.presentation.viewModel.NoteListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteEditFragment extends Fragment {
    private TextInputEditText titleNote;
    private MaterialTextView dateNote;
    private TextInputEditText textNote;
    private NoteListViewModel viewModel;
    private NavController navController;
    DatePickerDialog.OnDateSetListener mDateListener;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        viewModel = new ViewModelProvider(requireActivity()).get(NoteListViewModel.class);
        navController = Navigation.findNavController(view);
        MaterialButton buttonEditNote = view.findViewById(R.id.note_edit__button_edit);
        titleNote = view.findViewById(R.id.note_edit__title);
        textNote = view.findViewById(R.id.note_edit__text);
        dateNote = view.findViewById(R.id.note_edit__date);

        if (viewModel.getSelectedNote().getValue() != null) {
            titleNote.setText(viewModel.getSelectedNote().getValue().getTitle());
            textNote.setText(viewModel.getSelectedNote().getValue().getText());
            dateNote.setText(sdf.format(viewModel.getSelectedNote().getValue().getDate()));
        }

        mDateListener = (datePicker, year, month, day) -> {
            Date newDate = viewModel.getSelectedNote().getValue().getDate();
            Date tempDate = null;
            try {
                tempDate = sdf.parse(day + "." + (month + 1) + "." + year + " " + newDate.getHours() + ":" + newDate.getMinutes());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (tempDate != null) {
                dateNote.setText(sdf.format(tempDate));
            }
        };
        dateNote.setOnClickListener(v -> {
            Date newDate = null;
            try {
                newDate = sdf.parse(dateNote.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (newDate != null) {
                cal.setTime(newDate);
            }
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateListener,
                    year, month, day
            );
            dialog.show();
        });
        buttonEditNote.setOnClickListener(v -> {
            String title = getElementText(titleNote);
            String text = getElementText(textNote);
            String date = getElementText(dateNote);

            if (title.equals("") || text.equals("")) return;

            Note currentNote = viewModel.getSelectedNote().getValue();
            currentNote.setTitle(title);
            currentNote.setText(text);
            try {
                currentNote.setDate(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            viewModel.updateNote(currentNote);
            navController.popBackStack();
        });
    }

    private String getElementText(TextInputEditText textEdit) {
        return (textEdit.getText() != null) ? textEdit.getText().toString().trim() : "";
    }

    private String getElementText(MaterialTextView textEdit) {
        return (textEdit.getText() != null) ? textEdit.getText().toString().trim() : "";
    }

}
