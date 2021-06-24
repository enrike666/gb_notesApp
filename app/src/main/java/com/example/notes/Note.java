package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private int id;
    private String name;
    private String createDate;

    private String description;

    public Note(int id, String name, String createDate, String description) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.description = description;
    }


    protected Note(Parcel in) {
        id = in.readInt();
        name = in.readString();
        createDate = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
        dest.writeString(getCreateDate());
        dest.writeString(getDescription());
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDescription() {
        return description;
    }
}
