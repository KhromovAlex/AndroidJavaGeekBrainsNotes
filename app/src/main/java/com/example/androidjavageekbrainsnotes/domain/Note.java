package com.example.androidjavageekbrainsnotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
    private int titleRes;
    private int textRes;
    private Date date;

    public Note(int titleRes, int textRes, Date date) {
        this.titleRes = titleRes;
        this.textRes = textRes;
        this.date = date;
    }

    protected Note(Parcel in) {
        titleRes = in.readInt();
        textRes = in.readInt();
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

    public int getTitleRes() {
        return titleRes;
    }

    public int getTextRes() {
        return textRes;
    }

    public Date getDate() {
        return date;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public void setTextRes(int textRes) {
        this.textRes = textRes;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titleRes);
        dest.writeInt(textRes);
    }
}
