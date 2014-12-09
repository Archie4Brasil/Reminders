package com.anrlabs.reminders;

/**
 * Created by Grand on 12/9/2014.
 */
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper
{

    static final String TITLE = "title";
    static final String MESSAGE = "message";
    static final String DATE = "date";
    static final String TIME = "time";
    static final String LOCATION = "location";
    static final String TABLE = "reminders";
    private static final String DATABASE_NAME = "db";

    public DatabaseHelper(Context context, CursorFactory factory,
                          int version) {
        super(context, DATABASE_NAME, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE +
                " TEXT, "+ MESSAGE + "TEXT, " + DATE + " TEXT, "+ TIME + "TEXT, " + LOCATION +
                " TEXT);");

        ContentValues cv = new ContentValues();

        cv.put(TITLE, "Shopping");
        cv.put(MESSAGE,"Get Milk");
        cv.put(DATE, "12/9/2014");
        cv.put(TIME, "2:00PM");
        cv.put(LOCATION, "x,y");
        db.insert(TABLE, TITLE, cv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w(TABLE, "Upgrading database, which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }
}
