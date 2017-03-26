package com.dummies.tasks.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URI;

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

    //Content Provider Uri und Quelle
    public static final String AUTHORITY
            = "com.dummies.tasks.provider.TaskProvider";
    public static final Uri CONTENT_URI
            = Uri.parse("content://" + AUTHORITY + "/task");

    //MIME-Typen für die Auflistung von Tasks oder
    //die Suche nach einem einzelnen Task
    private static final String TASKS_MIME_TYPE
            = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.dummies.tasks.tasks";
    private static final String TASK_MIME_TYPE
            = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.dummies.tasks.task";
    //UriMatcher-Schnickschnack
    private static final int LIST_TASK = 0;
    private static final int ITEM_TASK = 1;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    /**
     * Erzeugt einen UriMatcher für Suchvorschläge und abkürzende Aktualisierungsanfragen.
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "task", LIST_TASK);
        matcher.addURI(AUTHORITY, "task/#", ITEM_TASK);
        return matcher;
    }

    /**
     * Diese Methode wird benötogt, um die unterstützten Typen abzufragen.
     */
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case LIST_TASK:
                return TASKS_MIME_TYPE;
            case ITEM_TASK:
                return TASK_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        //Eine Verbindung zu unserer Datenbank einrichten
        db = new DatabaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //Sie können keine eigene Task-ID wählen
        if (values.containsKey(COLUMN_TASKID)) {
            throw new UnsupportedOperationException();
        }

        long id = db.insertOrThrow(DATABASE_TABLE, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Diese Methode wird aufgerufen, wenn jemand etwas in unserem
     * ContentProvider aktualisieren will.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String ignored1, @Nullable String[] ignored2) {
        //Task-ID kann nicht geändert werden
        if (values.containsKey(COLUMN_TASKID)) {
            throw new UnsupportedOperationException();
        }
        int count = db.update(
                DATABASE_TABLE,
                values,
                COLUMN_TASKID + "=?",
                new String[]{Long.toString(ContentUris.parseId(uri))});
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
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