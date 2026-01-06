// ==================== ReservationDAO.java ====================
package com.campplanner.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.campplanner.app.models.Reservation;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private DatabaseHelper dbHelper;

    public ReservationDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addReservation(Reservation reservation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_RESERVATION_TRIP_ID, reservation.getTripId());
        values.put(DatabaseHelper.COL_RESERVATION_NAME, reservation.getName());
        values.put(DatabaseHelper.COL_RESERVATION_TYPE, reservation.getType());
        values.put(DatabaseHelper.COL_RESERVATION_DATE, reservation.getDate());
        values.put(DatabaseHelper.COL_RESERVATION_PRICE, reservation.getPrice());
        values.put(DatabaseHelper.COL_RESERVATION_STATUS, reservation.getStatus());
        values.put(DatabaseHelper.COL_RESERVATION_CONFIRMATION, reservation.getConfirmationNumber());

        long id = db.insert(DatabaseHelper.TABLE_RESERVATIONS, null, values);
        db.close();
        return id;
    }

    public List<Reservation> getReservationsForTrip(int tripId) {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RESERVATIONS, null,
                DatabaseHelper.COL_RESERVATION_TRIP_ID + "=?",
                new String[]{String.valueOf(tripId)},
                null, null, DatabaseHelper.COL_RESERVATION_DATE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_TRIP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_DATE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESERVATION_CONFIRMATION))
                );
                reservations.add(reservation);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return reservations;
    }

    public int updateReservation(Reservation reservation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_RESERVATION_NAME, reservation.getName());
        values.put(DatabaseHelper.COL_RESERVATION_TYPE, reservation.getType());
        values.put(DatabaseHelper.COL_RESERVATION_DATE, reservation.getDate());
        values.put(DatabaseHelper.COL_RESERVATION_PRICE, reservation.getPrice());
        values.put(DatabaseHelper.COL_RESERVATION_STATUS, reservation.getStatus());
        values.put(DatabaseHelper.COL_RESERVATION_CONFIRMATION, reservation.getConfirmationNumber());

        int rows = db.update(DatabaseHelper.TABLE_RESERVATIONS, values,
                DatabaseHelper.COL_RESERVATION_ID + "=?",
                new String[]{String.valueOf(reservation.getId())});
        db.close();
        return rows;
    }

    public int deleteReservation(int reservationId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_RESERVATIONS,
                DatabaseHelper.COL_RESERVATION_ID + "=?",
                new String[]{String.valueOf(reservationId)});
        db.close();
        return rows;
    }
}