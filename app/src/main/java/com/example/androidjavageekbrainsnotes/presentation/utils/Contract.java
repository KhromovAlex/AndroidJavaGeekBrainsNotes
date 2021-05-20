package com.example.androidjavageekbrainsnotes.presentation.utils;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

public class Contract extends ActivityResultContract<GoogleSignInClient, Task<GoogleSignInAccount>> {
    @NonNull
    @NotNull
    @Override
    public Intent createIntent(@NonNull @NotNull Context context, GoogleSignInClient input) {
        return input.getSignInIntent();
    }

    @Override
    public Task<GoogleSignInAccount> parseResult(int resultCode, @Nullable Intent intent) {
        return GoogleSignIn.getSignedInAccountFromIntent(intent);
    }
}
