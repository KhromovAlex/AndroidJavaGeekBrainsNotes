package com.example.androidjavageekbrainsnotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.androidjavageekbrainsnotes.domain.Note;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteClicked {
    private boolean isLandscape = false;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        fragmentManager = getSupportFragmentManager();

        if (!isLandscape) {
            Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);

            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new NoteListFragment())
                        .commit();
            }
        } else {
            fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment);

            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.details_fragment, new NoteDetailsFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onNoteClicked(Note note) {
        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, new NoteDetailsFragment())
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new NoteDetailsFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
