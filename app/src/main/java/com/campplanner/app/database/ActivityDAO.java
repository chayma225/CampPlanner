// ==================== ActivityDAO.java ====================
package com.campplanner.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.campplanner.app.models.Activity;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
    private DatabaseHelper dbHelper;

    public ActivityDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addActivity(Activity activity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_ACTIVITY_TRIP_ID, activity.getTripId());
        values.put(DatabaseHelper.COL_ACTIVITY_NAME, activity.getName());
        values.put(DatabaseHelper.COL_ACTIVITY_DATE, activity.getDate());
        values.put(DatabaseHelper.COL_ACTIVITY_TIME, activity.getTime());
        values.put(DatabaseHelper.COL_ACTIVITY_LOCATION, activity.getLocation());
        values.put(DatabaseHelper.COL_ACTIVITY_DESCRIPTION, activity.getDescription());
        values.put(DatabaseHelper.COL_ACTIVITY_COMPLETED, activity.isCompleted() ? 1 : 0);

        long id = db.insert(DatabaseHelper.TABLE_ACTIVITIES, null, values);
        db.close();
        return id;
    }

    public List<Activity> getActivitiesForTrip(int tripId) {
        List<Activity> activities = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_ACTIVITIES, null,
                DatabaseHelper.COL_ACTIVITY_TRIP_ID + "=?", new String[]{String.valueOf(tripId)},
                null, null, DatabaseHelper.COL_ACTIVITY_DATE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Activity activity = new Activity(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_TRIP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVITY_COMPLETED)) == 1
                );
                activities.add(activity);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return activities;
    }

    public int updateActivity(Activity activity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_ACTIVITY_NAME, activity.getName());
        values.put(DatabaseHelper.COL_ACTIVITY_DATE, activity.getDate());
        values.put(DatabaseHelper.COL_ACTIVITY_TIME, activity.getTime());
        values.put(DatabaseHelper.COL_ACTIVITY_LOCATION, activity.getLocation());
        values.put(DatabaseHelper.COL_ACTIVITY_DESCRIPTION, activity.getDescription());
        values.put(DatabaseHelper.COL_ACTIVITY_COMPLETED, activity.isCompleted() ? 1 : 0);

        int rows = db.update(DatabaseHelper.TABLE_ACTIVITIES, values,
                DatabaseHelper.COL_ACTIVITY_ID + "=?",
                new String[]{String.valueOf(activity.getId())});
        db.close();
        return rows;
    }

    public int deleteActivity(int activityId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_ACTIVITIES,
                DatabaseHelper.COL_ACTIVITY_ID + "=?",
                new String[]{String.valueOf(activityId)});
        db.close();
        return rows;
    }
}