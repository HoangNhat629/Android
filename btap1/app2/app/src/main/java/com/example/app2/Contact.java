package com.example.app2;

public class Contact {
    private String mName;
    private String mNumber;

    public Contact(String mName, String mNumber) {
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}