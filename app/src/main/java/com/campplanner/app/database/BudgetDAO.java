// ==================== BudgetDAO.java ====================
package com.campplanner.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.campplanner.app.database.DatabaseHelper;
import com.campplanner.app.models.BudgetCategory;
import com.campplanner.app.models.Transaction;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private DatabaseHelper dbHelper;

    public BudgetDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Budget Categories
    public long addCategory(BudgetCategory category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_CATEGORY_TRIP_ID, category.getTripId());
        values.put(DatabaseHelper.COL_CATEGORY_NAME, category.getName());
        values.put(DatabaseHelper.COL_CATEGORY_BUDGET, category.getBudget());
        values.put(DatabaseHelper.COL_CATEGORY_SPENT, category.getSpent());
        values.put(DatabaseHelper.COL_CATEGORY_COLOR, category.getColor());

        long id = db.insert(DatabaseHelper.TABLE_BUDGET_CATEGORIES, null, values);
        db.close();
        return id;
    }

    public List<BudgetCategory> getCategoriesForTrip(int tripId) {
        List<BudgetCategory> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BUDGET_CATEGORIES, null,
                DatabaseHelper.COL_CATEGORY_TRIP_ID + "=?", new String[]{String.valueOf(tripId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                BudgetCategory category = new BudgetCategory(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_TRIP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_BUDGET)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_SPENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_COLOR))
                );
                categories.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return categories;
    }

    public int updateCategory(BudgetCategory category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_CATEGORY_NAME, category.getName());
        values.put(DatabaseHelper.COL_CATEGORY_BUDGET, category.getBudget());
        values.put(DatabaseHelper.COL_CATEGORY_SPENT, category.getSpent());
        values.put(DatabaseHelper.COL_CATEGORY_COLOR, category.getColor());

        int rows = db.update(DatabaseHelper.TABLE_BUDGET_CATEGORIES, values,
                DatabaseHelper.COL_CATEGORY_ID + "=?",
                new String[]{String.valueOf(category.getId())});
        db.close();
        return rows;
    }

    // Transactions
    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TRANSACTION_CATEGORY_ID, transaction.getCategoryId());
        values.put(DatabaseHelper.COL_TRANSACTION_DESCRIPTION, transaction.getDescription());
        values.put(DatabaseHelper.COL_TRANSACTION_AMOUNT, transaction.getAmount());
        values.put(DatabaseHelper.COL_TRANSACTION_DATE, transaction.getDate());
        values.put(DatabaseHelper.COL_TRANSACTION_TYPE, transaction.getType());

        long id = db.insert(DatabaseHelper.TABLE_TRANSACTIONS, null, values);

        // Update category spent amount
        BudgetCategory category = getCategoryById(transaction.getCategoryId());
        if (category != null) {
            category.setSpent(category.getSpent() + transaction.getAmount());
            updateCategory(category);
        }

        db.close();
        return id;
    }

    public List<Transaction> getTransactionsForCategory(int categoryId) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TRANSACTIONS, null,
                DatabaseHelper.COL_TRANSACTION_CATEGORY_ID + "=?",
                new String[]{String.valueOf(categoryId)},
                null, null, DatabaseHelper.COL_TRANSACTION_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANSACTION_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANSACTION_CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANSACTION_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANSACTION_AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANSACTION_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANSACTION_TYPE))
                );
                transactions.add(transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return transactions;
    }

    private BudgetCategory getCategoryById(int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BUDGET_CATEGORIES, null,
                DatabaseHelper.COL_CATEGORY_ID + "=?", new String[]{String.valueOf(categoryId)},
                null, null, null);

        BudgetCategory category = null;
        if (cursor != null && cursor.moveToFirst()) {
            category = new BudgetCategory(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_TRIP_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_BUDGET)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_SPENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_COLOR))
            );
            cursor.close();
        }
        db.close();
        return category;
    }
}