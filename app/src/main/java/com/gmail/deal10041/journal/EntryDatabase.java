package com.gmail.deal10041.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Dylan Wellner on 27-2-2018.
 * Database helper
 */

public class EntryDatabase extends SQLiteOpenHelper {

    private static EntryDatabase instance = null;

    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create new table
        String q = "CREATE TABLE 'entries' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'title' TEXT, 'content' TEXT, 'mood' TEXT, 'timestamp' DATETIME DEFAULT CURRENT_TIMESTAMP, 'starred' BIT)";
        sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop table
        String q = "DROP TABLE IF EXISTS 'entries'";
        sqLiteDatabase.execSQL(q);

        // create new table
        onCreate(sqLiteDatabase);
    }

    public static EntryDatabase getInstance(Context context) {
        // create new database, if none were made
        if (instance == null) {
            instance = new EntryDatabase(context, "db", null, 1);
        }

        return instance;
    }

    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("SELECT * FROM 'entries' ORDER BY _id DESC", null);
    }

    public void insert(JournalEntry entry) {
        // connect to database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // collect written values
        ContentValues vals = new ContentValues();
        vals.put("title", entry.getTitle());
        vals.put("content", entry.getContent());
        vals.put("mood", entry.getMood());
        vals.put("starred", 0);

        // insert values into database
        sqLiteDatabase.insert("entries", null, vals);
    }

    public void delete(long id) {
        // connect to database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // delete row with corresponding id
        String[] whereArgs = {Objects.toString(id)};
        sqLiteDatabase.delete("entries", "_id = ?", whereArgs);
    }

    public void star(long id, boolean starred) {
        // connect to database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // put boolean in contentvalues
        ContentValues vals = new ContentValues();
        if (starred) {
            vals.put("starred", 1);
        }
        else {
            vals.put("starred", 0);
        }

        // update starred value
        String[] whereArgs = {Objects.toString(id)};
        sqLiteDatabase.update("entries", vals, "_id = ?", whereArgs);
    }

    public Cursor selectStarred() {
        return getWritableDatabase().rawQuery("SELECT * FROM 'entries' WHERE starred=? ORDER BY _id DESC", new String[] {"1"});
    }
}
