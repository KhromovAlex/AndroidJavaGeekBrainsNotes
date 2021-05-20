package com.example.androidjavageekbrainsnotes.domain.repository;

public interface Callback<T> {

    void onSuccess(T value);

    void onError(Throwable error);
}
