package com.architecture.androidlivedata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntryDBHelper extends SQLiteOpenHelper {

    public EntryDBHelper(Context context) {
        super(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + DbSettings.DBEntry.TABLE + " ( " +
                DbSettings.DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSettings.DBEntry.COL_NAME + " TEXT NOT NULL, " +
                DbSettings.DBEntry.COL_PROFESSION + " TEXT NOT NULL, " +
                DbSettings.DBEntry.COL_COMPANY + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSettings.DBEntry.TABLE);
        onCreate(db);
    }

}