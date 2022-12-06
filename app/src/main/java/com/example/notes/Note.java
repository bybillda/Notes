package com.example.notes;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Note implements /*Serializable*/ Parcelable {
    private static final Random random = new Random();
    private static ArrayList<Note> notes;

    private static int counter;

    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    {
        id = ++counter;
    }

    static {
        notes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            notes.add(Note.getNote(i));
        }
    }

    public Note(String title, String description, LocalDateTime creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }


    @SuppressLint("DefaultLocale")
    public static Note getNote(int index) {
        String title = String.format("Заметка %d", index);
        String description = String.format("Описание заметки %d", index);
        LocalDateTime creationDate = LocalDateTime.now().plusDays(-random.nextInt(5));
        return new Note(title, description, creationDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getTitle());
        parcel.writeString(getDescription());
    }

    protected Note(Parcel parcel){
        id = parcel.readInt();
        title = parcel.readString();
        description = parcel.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel parcel) {
            return new Note(parcel);
        }

        @Override
        public Note[] newArray(int i) {
            return new Note[0];
        }
    };

}