// ==================== JournalDAO.java ====================
package com.campplanner.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.campplanner.app.models.JournalEntry;
import java.util.ArrayList;
import java.util.List;

public class JournalDAO {
    private DatabaseHelper dbHelper;

    public JournalDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addEntry(JournalEntry entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_JOURNAL_TRIP_ID, entry.getTripId());
        values.put(DatabaseHelper.COL_JOURNAL_TITLE, entry.getTitle());
        values.put(DatabaseHelper.COL_JOURNAL_CONTENT, entry.getContent());
        values.put(DatabaseHelper.COL_JOURNAL_DATE, entry.getDate());
        values.put(DatabaseHelper.COL_JOURNAL_LOCATION, entry.getLocation());
        values.put(DatabaseHelper.COL_JOURNAL_PHOTO_PATH, entry.getPhotoPath());

        long id = db.insert(DatabaseHelper.TABLE_JOURNAL_ENTRIES, null, values);
        db.close();
        return id;
    }

    public List<JournalEntry> getEntriesForTrip(int tripId) {
        List<JournalEntry> entries = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_JOURNAL_ENTRIES, null,
                DatabaseHelper.COL_JOURNAL_TRIP_ID + "=?",
                new String[]{String.valueOf(tripId)},
                null, null, DatabaseHelper.COL_JOURNAL_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                JournalEntry entry = new JournalEntry(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_TRIP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_CONTENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_JOURNAL_PHOTO_PATH))
                );
                entries.add(entry);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return entries;
    }

    public int updateEntry(JournalEntry entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_JOURNAL_TITLE, entry.getTitle());
        values.put(DatabaseHelper.COL_JOURNAL_CONTENT, entry.getContent());
        values.put(DatabaseHelper.COL_JOURNAL_DATE, entry.getDate());
        values.put(DatabaseHelper.COL_JOURNAL_LOCATION, entry.getLocation());
        values.put(DatabaseHelper.COL_JOURNAL_PHOTO_PATH, entry.getPhotoPath());

        int rows = db.update(DatabaseHelper.TABLE_JOURNAL_ENTRIES, values,
                DatabaseHelper.COL_JOURNAL_ID + "=?",
                new String[]{String.valueOf(entry.getId())});
        db.close();
        return rows;
    }

    public int deleteEntry(int entryId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_JOURNAL_ENTRIES,
                DatabaseHelper.COL_JOURNAL_ID + "=?",
                new String[]{String.valueOf(entryId)});
        db.close();
        return rows;
    }
}