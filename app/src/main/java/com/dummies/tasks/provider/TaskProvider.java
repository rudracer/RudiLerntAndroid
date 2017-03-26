package com.dummies.tasks.provider;

import android.content.ContentProvider;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Rudi on 26.03.2017.
 */

public class TaskProvider extends ContentProvider {

    //Datenbankspalten
    public static final String COLUMN_TASKID = "_id";
    public static final String COLUMN_DATE_TIME = "task_date_time";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_TITLE = "title";

    //Datenbankbezogene Konstanten
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "tasks";

    //Die eigentliche Datenbank
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        //Eine Verbindung zu unserer Datenbank einrichten
        db = new DatabaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    protected static class DatabaseHelper extends SQLiteOpenHelper {
        static final String DATEBASE_CREATE =
                "create table " + DATABASE_TABLE + " (" +
                        COLUMN_TASKID + " integer primary key autoincrement, " +
                        COLUMN_TITLE + " text not null, " +
                        COLUMN_NOTES + " text not null, " +
                        COLUMN_DATE_TIME + " integer not null);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATEBASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            throw new UnsupportedOperationException();
        }
    }
}