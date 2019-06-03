package com.architecture.androidlivedata.model;

public class UserData {

    public long mId;
    public String mName;
    public String mProfession;
    public String mCompany;

    public UserData(long id, String name, String profession, String company) {
        mId = id;
        mName = name;
        mProfession = profession;
        mCompany = company;
    }

    public UserData(UserData userData) {
        mId = userData.mId;
        mName = userData.mName;
        mProfession = userData.mProfession;
        mCompany = userData.mCompany;
    }

}