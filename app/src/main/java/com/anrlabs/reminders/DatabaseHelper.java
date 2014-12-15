package com.anrlabs.reminders;

/**
 * Created by Grand on 12/9/2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    static final String ID = "_id";
    static final String TITLE = "title";
    static final String MESSAGE = "message";
    static final String DATE = "date";
    static final String TIME = "time";
    static final String XCOORDS = "xcoords";
    static final String YCOORDS = "ycoords";
    static final String RADIUS = "radius";
    static final String TABLE = "reminders";
    private static final String DATABASE_NAME = "db";

    private static final int DATABASE_VERSION = 1;
    Cursor cursor;

    SQLiteDatabase db;

    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE +
                " TEXT, " + MESSAGE + " TEXT, " + DATE + " TEXT, " + TIME + " TEXT, " + XCOORDS +
                " TEXT, " + YCOORDS + " TEXT, " + RADIUS + " TEXT);");
    }

    public static DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void addData(ContentValues cv) {
        db = this.getWritableDatabase();

        db.insert(TABLE, TITLE, cv);

        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w(TABLE, "Upgrading database, which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }

    public void insert(ContentValues cv) {
        this.getWritableDatabase().insert(TABLE, TITLE, cv);
    }


    public void deleteData(long dataItem) {
        this.getWritableDatabase().delete(TABLE, "_id = " + dataItem, null);
    }

    public Cursor loadReminders() {
        cursor = (SQLiteCursor) this.getReadableDatabase().rawQuery("SELECT " + DatabaseHelper.ID + ", "
                + DatabaseHelper.TITLE + ", " + DatabaseHelper.MESSAGE + ", "
                + DatabaseHelper.DATE + ", " + DatabaseHelper.TIME + ", "
                + DatabaseHelper.XCOORDS + ", " + DatabaseHelper.YCOORDS + ", " + DatabaseHelper.RADIUS +
                " FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.ID  + " DESC ", null);
        return cursor;

    }

     public  Cursor editReminders(long id) {
            SQLiteDatabase db = this.getReadableDatabase();
         if( cursor != null) {
             cursor = db.query(DatabaseHelper.TABLE, new String[]{DatabaseHelper.ID,
                DatabaseHelper.TITLE, DatabaseHelper.MESSAGE, DatabaseHelper.DATE,
                DatabaseHelper.TIME, DatabaseHelper.XCOORDS, DatabaseHelper.YCOORDS,
                DatabaseHelper.RADIUS}, DatabaseHelper.ID + "=?", new String[]{String.valueOf(id)},
                null,null,null);
         }
         return cursor;
     }
}
