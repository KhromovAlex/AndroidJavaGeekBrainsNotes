package com.example.androidjavageekbrainsnotes.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidjavageekbrainsnotes.R;
import com.example.androidjavageekbrainsnotes.presentation.utils.Contract;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

public class AuthFragment extends Fragment {
    private NavController navController;
    GoogleSignInClient client;
    ActivityResultLauncher<GoogleSignInClient> launcher;

    public AuthFragment() {
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireContext(), options);

        launcher = registerForActivityResult(new Contract(), result -> {
            if (result == null) return;

            try {
                GoogleSignInAccount account = result.getResult(ApiException.class);
                if (account != null) {
                    navController.navigate(R.id.nav_home);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.sign_in_google).setOnClickListener(v -> {
            launcher.launch(client);
        });
    }
}
