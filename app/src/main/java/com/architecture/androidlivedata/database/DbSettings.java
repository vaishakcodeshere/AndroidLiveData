package com.architecture.androidlivedata.database;

import android.provider.BaseColumns;

public class DbSettings {

    public static final String DB_NAME = "entry.db";
    public static final int DB_VERSION = 1;

    public class DBEntry implements BaseColumns {

        public static final String TABLE = "entry";
        public static final String COL_NAME = "name";
        public static final String COL_PROFESSION = "profession";
        public static final String COL_COMPANY = "company";

    }
}