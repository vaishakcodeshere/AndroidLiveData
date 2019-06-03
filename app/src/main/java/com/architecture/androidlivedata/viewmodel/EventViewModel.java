package com.architecture.androidlivedata.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.architecture.androidlivedata.database.DbSettings;
import com.architecture.androidlivedata.database.EntryDBHelper;
import com.architecture.androidlivedata.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EntryDBHelper entryDBHelper;
    private MutableLiveData<List<UserData>> mFavs;

    public EventViewModel(Application application) {
        super(application);
        entryDBHelper = new EntryDBHelper(application);
    }

    public MutableLiveData<List<UserData>> getFavs() {
        if (mFavs == null) {
            mFavs = new MutableLiveData<>();
            loadFavs();
        }

        return mFavs;
    }

    private void loadFavs() {
        List<UserData> newFavs = new ArrayList<>();
        SQLiteDatabase db = entryDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DbSettings.DBEntry.TABLE,
                new String[]{
                        DbSettings.DBEntry._ID,
                        DbSettings.DBEntry.COL_NAME,
                        DbSettings.DBEntry.COL_PROFESSION,
                        DbSettings.DBEntry.COL_COMPANY
                },
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idxId = cursor.getColumnIndex(DbSettings.DBEntry._ID);
            int idxName = cursor.getColumnIndex(DbSettings.DBEntry.COL_NAME);
            int idxProfession = cursor.getColumnIndex(DbSettings.DBEntry.COL_PROFESSION);
            int idxCompany = cursor.getColumnIndex(DbSettings.DBEntry.COL_COMPANY);
            newFavs.add(new UserData(cursor.getLong(idxId), cursor.getString(idxName), cursor.getString(idxProfession), cursor.getString(idxCompany)));
        }

        cursor.close();
        db.close();
        mFavs.setValue(newFavs);
    }


    public void addFav(String name, String profession, String company) {

        SQLiteDatabase db = entryDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbSettings.DBEntry.COL_NAME, name);
        values.put(DbSettings.DBEntry.COL_PROFESSION, profession);
        values.put(DbSettings.DBEntry.COL_COMPANY, company);
        long id = db.insertWithOnConflict(DbSettings.DBEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();


        List<UserData> UserData = mFavs.getValue();

        ArrayList<UserData> clonedFavs;
        if (UserData == null) {
            clonedFavs = new ArrayList<>();
        } else {
            clonedFavs = new ArrayList<>(UserData.size());
            for (int i = 0; i < UserData.size(); i++) {
                clonedFavs.add(new UserData(UserData.get(i)));
            }
        }

        UserData fav = new UserData(id, name, profession, company);
        clonedFavs.add(fav);
        mFavs.setValue(clonedFavs);
    }

    public void removeFav(long id) {
        SQLiteDatabase db = entryDBHelper.getWritableDatabase();
        db.delete(
                DbSettings.DBEntry.TABLE,
                DbSettings.DBEntry._ID + " = ?",
                new String[]{Long.toString(id)}
        );
        db.close();

        List<UserData> favs = mFavs.getValue();
        ArrayList<UserData> clonedFavs = new ArrayList<>(favs.size());
        for (int i = 0; i < favs.size(); i++) {
            clonedFavs.add(new UserData(favs.get(i)));
        }

        int index = -1;
        for (int i = 0; i < clonedFavs.size(); i++) {
            UserData UserData = clonedFavs.get(i);
            if (UserData.mId == id) {
                index = i;
            }
        }
        if (index != -1) {
            clonedFavs.remove(index);
        }
        mFavs.setValue(clonedFavs);
    }

}