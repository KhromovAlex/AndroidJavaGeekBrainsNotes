package com.example.androidjavageekbrainsnotes.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note implements Parcelable {
    private final String id;
    private String title;
    private String text;
    private Date date;

    public Note(String id, String title, String text, Date date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    protected Note(Parcel in) {
        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        id = in.readString();
        title = in.readString();
        text = in.readString();
        try {
            date = sdf.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(sdf.format(date));
    }
}
