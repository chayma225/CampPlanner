package com.campplanner.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.campplanner.app.models.EquipmentItem;
import com.campplanner.app.models.EquipmentList;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {
    private DatabaseHelper dbHelper;

    public EquipmentDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // ==================== Equipment Lists ====================

    public long addEquipmentList(EquipmentList list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_EQUIPMENT_LIST_TRIP_ID, list.getTripId());
        values.put(DatabaseHelper.COL_EQUIPMENT_LIST_NAME, list.getName());

        long id = db.insert(DatabaseHelper.TABLE_EQUIPMENT_LISTS, null, values);
        db.close();
        return id;
    }

    public List<EquipmentList> getListsForTrip(int tripId) {
        List<EquipmentList> lists = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EQUIPMENT_LISTS, null,
                DatabaseHelper.COL_EQUIPMENT_LIST_TRIP_ID + "=?",
                new String[]{String.valueOf(tripId)},
                null, null, DatabaseHelper.COL_EQUIPMENT_LIST_CREATED_AT + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                EquipmentList list = new EquipmentList(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_TRIP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_CREATED_AT))
                );
                lists.add(list);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lists;
    }

    public EquipmentList getListById(int listId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EQUIPMENT_LISTS, null,
                DatabaseHelper.COL_EQUIPMENT_LIST_ID + "=?",
                new String[]{String.valueOf(listId)},
                null, null, null);

        EquipmentList list = null;
        if (cursor != null && cursor.moveToFirst()) {
            list = new EquipmentList(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_TRIP_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_LIST_CREATED_AT))
            );
            cursor.close();
        }
        db.close();
        return list;
    }

    public int deleteEquipmentList(int listId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_EQUIPMENT_LISTS,
                DatabaseHelper.COL_EQUIPMENT_LIST_ID + "=?",
                new String[]{String.valueOf(listId)});
        db.close();
        return rows;
    }

    // ==================== Equipment Items ====================

    public long addEquipmentItem(EquipmentItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_LIST_ID, item.getListId());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_NAME, item.getName());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_CATEGORY, item.getCategory());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_PACKED, item.isPacked() ? 1 : 0);

        long id = db.insert(DatabaseHelper.TABLE_EQUIPMENT_ITEMS, null, values);
        db.close();
        return id;
    }

    public List<EquipmentItem> getItemsForList(int listId) {
        List<EquipmentItem> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EQUIPMENT_ITEMS, null,
                DatabaseHelper.COL_EQUIPMENT_ITEM_LIST_ID + "=?",
                new String[]{String.valueOf(listId)},
                null, null, DatabaseHelper.COL_EQUIPMENT_ITEM_CATEGORY + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                EquipmentItem item = new EquipmentItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_ITEM_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_ITEM_LIST_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_ITEM_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_ITEM_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_ITEM_QUANTITY)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EQUIPMENT_ITEM_PACKED)) == 1
                );
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return items;
    }

    public int updateEquipmentItem(EquipmentItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_NAME, item.getName());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_CATEGORY, item.getCategory());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_PACKED, item.isPacked() ? 1 : 0);

        int rows = db.update(DatabaseHelper.TABLE_EQUIPMENT_ITEMS, values,
                DatabaseHelper.COL_EQUIPMENT_ITEM_ID + "=?",
                new String[]{String.valueOf(item.getId())});
        db.close();
        return rows;
    }

    public int toggleItemPacked(int itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get current state
        Cursor cursor = db.query(DatabaseHelper.TABLE_EQUIPMENT_ITEMS,
                new String[]{DatabaseHelper.COL_EQUIPMENT_ITEM_PACKED},
                DatabaseHelper.COL_EQUIPMENT_ITEM_ID + "=?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        boolean currentState = false;
        if (cursor != null && cursor.moveToFirst()) {
            currentState = cursor.getInt(0) == 1;
            cursor.close();
        }

        // Toggle state
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_EQUIPMENT_ITEM_PACKED, currentState ? 0 : 1);

        int rows = db.update(DatabaseHelper.TABLE_EQUIPMENT_ITEMS, values,
                DatabaseHelper.COL_EQUIPMENT_ITEM_ID + "=?",
                new String[]{String.valueOf(itemId)});
        db.close();
        return rows;
    }

    public int deleteEquipmentItem(int itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_EQUIPMENT_ITEMS,
                DatabaseHelper.COL_EQUIPMENT_ITEM_ID + "=?",
                new String[]{String.valueOf(itemId)});
        db.close();
        return rows;
    }

    public int getPackedItemsCount(int listId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_EQUIPMENT_ITEMS +
                        " WHERE " + DatabaseHelper.COL_EQUIPMENT_ITEM_LIST_ID + "=? AND " +
                        DatabaseHelper.COL_EQUIPMENT_ITEM_PACKED + "=1",
                new String[]{String.valueOf(listId)}
        );

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getTotalItemsCount(int listId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_EQUIPMENT_ITEMS +
                        " WHERE " + DatabaseHelper.COL_EQUIPMENT_ITEM_LIST_ID + "=?",
                new String[]{String.valueOf(listId)}
        );

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }
}