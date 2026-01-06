// ==================== TripDAO.java ====================
package com.campplanner.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.campplanner.app.models.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripDAO {
    private DatabaseHelper dbHelper;

    public TripDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addTrip(Trip trip) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TRIP_USER_ID, trip.getUserId());
        values.put(DatabaseHelper.COL_TRIP_NAME, trip.getName());
        values.put(DatabaseHelper.COL_TRIP_LOCATION, trip.getLocation());
        values.put(DatabaseHelper.COL_TRIP_START_DATE, trip.getStartDate());
        values.put(DatabaseHelper.COL_TRIP_END_DATE, trip.getEndDate());
        values.put(DatabaseHelper.COL_TRIP_PARTICIPANTS, trip.getParticipants());
        values.put(DatabaseHelper.COL_TRIP_STATUS, trip.getStatus());
        values.put(DatabaseHelper.COL_TRIP_BUDGET, trip.getBudget());
        values.put(DatabaseHelper.COL_TRIP_DESCRIPTION, trip.getDescription());

        long id = db.insert(DatabaseHelper.TABLE_TRIPS, null, values);
        db.close();
        return id;
    }

    public List<Trip> getAllTripsForUser(int userId) {
        List<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TRIPS, null,
                DatabaseHelper.COL_TRIP_USER_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, DatabaseHelper.COL_TRIP_START_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Trip trip = new Trip(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_START_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_END_DATE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_PARTICIPANTS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_STATUS)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_BUDGET)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_CREATED_AT))
                );
                trips.add(trip);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return trips;
    }

    public Trip getTripById(int tripId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TRIPS, null,
                DatabaseHelper.COL_TRIP_ID + "=?", new String[]{String.valueOf(tripId)},
                null, null, null);

        Trip trip = null;
        if (cursor != null && cursor.moveToFirst()) {
            trip = new Trip(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_LOCATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_START_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_END_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_PARTICIPANTS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_STATUS)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_BUDGET)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRIP_CREATED_AT))
            );
            cursor.close();
        }
        db.close();
        return trip;
    }

    public int updateTrip(Trip trip) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TRIP_NAME, trip.getName());
        values.put(DatabaseHelper.COL_TRIP_LOCATION, trip.getLocation());
        values.put(DatabaseHelper.COL_TRIP_START_DATE, trip.getStartDate());
        values.put(DatabaseHelper.COL_TRIP_END_DATE, trip.getEndDate());
        values.put(DatabaseHelper.COL_TRIP_PARTICIPANTS, trip.getParticipants());
        values.put(DatabaseHelper.COL_TRIP_STATUS, trip.getStatus());
        values.put(DatabaseHelper.COL_TRIP_BUDGET, trip.getBudget());
        values.put(DatabaseHelper.COL_TRIP_DESCRIPTION, trip.getDescription());

        int rows = db.update(DatabaseHelper.TABLE_TRIPS, values,
                DatabaseHelper.COL_TRIP_ID + "=?",
                new String[]{String.valueOf(trip.getId())});
        db.close();
        return rows;
    }

    public int deleteTrip(int tripId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_TRIPS,
                DatabaseHelper.COL_TRIP_ID + "=?",
                new String[]{String.valueOf(tripId)});
        db.close();
        return rows;
    }
}