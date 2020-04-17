package com.example.PetFind;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Usermodel implements Parcelable {
    private String owner;
    private String petType;
    private String ownerState;
    private String petPicture;
    private String userID;
    public Usermodel(String owner, String petType, String ownerState) {
        this.owner = owner;
        this.petType = petType;
        this.ownerState = ownerState;
    }
    public Usermodel(String owner, String petType, String ownerState, String petPicture,String userID) {
        this.owner = owner;
        this.petType = petType;
        this.ownerState = ownerState;
        this.petPicture = petPicture;
        this.userID = userID;
    }

    protected Usermodel(Parcel in) {
        owner = in.readString();
        petType = in.readString();
        ownerState = in.readString();
        petPicture = in.readString();
        userID = in.readString();
    }

    public static final Creator<Usermodel> CREATOR = new Creator<Usermodel>() {
        @Override
        public Usermodel createFromParcel(Parcel in) {
            return new Usermodel(in);
        }

        @Override
        public Usermodel[] newArray(int size) {
            return new Usermodel[size];
        }
    };

    public String getOwner() {
        return owner;
    }

    public String getPetType() {
        return petType;
    }

    public Uri getPetPicture() {
        return Uri.parse(petPicture);
    }

    public String getOwnerState() {
        return ownerState;
    }
    public String getUserID(){
        return userID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(owner);
        dest.writeString(petType);
        dest.writeString(ownerState);
        dest.writeString(petPicture);
        dest.writeString(userID);
    }
}
