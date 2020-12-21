package com.example.idflist;

import android.graphics.Bitmap;

public class User {
    private String userID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Bitmap logImage;
    private String type;

    public User(String uid, String name, String phoneNumber, String email, boolean b){

    }

    public User(String UID, String fullName, String phoneNumber, String email, String type){
        this.userID = UID; this.fullName = fullName; this.phoneNumber = phoneNumber; this.email = email; this.type = type;
    }

    public String getUserID(){
        return userID;
    }

    public void setImage(Bitmap bitmap){
        this.logImage = bitmap;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String age) {
        this.phoneNumber = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getLogImage() {
        return logImage;
    }
}
