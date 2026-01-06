package com.campplanner.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CampPlanner.db";
    private static final int DATABASE_VERSION = 1;

    // ============================
    // TABLE USERS
    // ============================
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_FIRST_NAME = "first_name";
    public static final String COL_USER_LAST_NAME = "last_name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_CREATED_AT = "created_at";

    // ============================
    // TABLE TRIPS
    // ============================
    public static final String TABLE_TRIPS = "trips";
    public static final String COL_TRIP_ID = "id";
    public static final String COL_TRIP_USER_ID = "user_id";
    public static final String COL_TRIP_NAME = "trip_name";
    public static final String COL_TRIP_LOCATION = "location";
    public static final String COL_TRIP_START_DATE = "start_date";
    public static final String COL_TRIP_END_DATE = "end_date";
    public static final String COL_TRIP_PARTICIPANTS = "participants";
    public static final String COL_TRIP_STATUS = "status";
    public static final String COL_TRIP_BUDGET = "budget";
    public static final String COL_TRIP_DESCRIPTION = "description";
    public static final String COL_TRIP_CREATED_AT = "created_at";

    // ============================
    // TABLE ACTIVITIES
    // ============================
    public static final String TABLE_ACTIVITIES = "activities";
    public static final String COL_ACTIVITY_ID = "id";
    public static final String COL_ACTIVITY_TRIP_ID = "trip_id";
    public static final String COL_ACTIVITY_NAME = "activity_name";
    public static final String COL_ACTIVITY_DATE = "date";
    public static final String COL_ACTIVITY_TIME = "time";
    public static final String COL_ACTIVITY_LOCATION = "location";
    public static final String COL_ACTIVITY_DESCRIPTION = "description";
    public static final String COL_ACTIVITY_COMPLETED = "completed";

    // ============================
    // TABLE BUDGET CATEGORIES
    // ============================
    public static final String TABLE_BUDGET_CATEGORIES = "budget_categories";
    public static final String COL_CATEGORY_ID = "id";
    public static final String COL_CATEGORY_TRIP_ID = "trip_id";
    public static final String COL_CATEGORY_NAME = "category_name";
    public static final String COL_CATEGORY_BUDGET = "budget";
    public static final String COL_CATEGORY_SPENT = "spent";
    public static final String COL_CATEGORY_COLOR = "color";

    // ============================
    // TABLE TRANSACTIONS
    // ============================
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COL_TRANSACTION_ID = "id";
    public static final String COL_TRANSACTION_CATEGORY_ID = "category_id";
    public static final String COL_TRANSACTION_DESCRIPTION = "description";
    public static final String COL_TRANSACTION_AMOUNT = "amount";
    public static final String COL_TRANSACTION_DATE = "date";
    public static final String COL_TRANSACTION_TYPE = "type";

    // ============================
    // TABLE RESERVATIONS
    // ============================
    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String COL_RESERVATION_ID = "id";
    public static final String COL_RESERVATION_TRIP_ID = "trip_id";
    public static final String COL_RESERVATION_NAME = "reservation_name";
    public static final String COL_RESERVATION_TYPE = "type";
    public static final String COL_RESERVATION_DATE = "date";
    public static final String COL_RESERVATION_PRICE = "price";
    public static final String COL_RESERVATION_STATUS = "status";
    public static final String COL_RESERVATION_CONFIRMATION = "confirmation_number";

    // ============================
    // TABLE EQUIPMENT LISTS
    // ============================
    public static final String TABLE_EQUIPMENT_LISTS = "equipment_lists";
    public static final String COL_EQUIPMENT_LIST_ID = "id";
    public static final String COL_EQUIPMENT_LIST_TRIP_ID = "trip_id";
    public static final String COL_EQUIPMENT_LIST_NAME = "list_name";
    public static final String COL_EQUIPMENT_LIST_CREATED_AT = "created_at";

    // ============================
    // TABLE EQUIPMENT ITEMS
    // ============================
    public static final String TABLE_EQUIPMENT_ITEMS = "equipment_items";
    public static final String COL_EQUIPMENT_ITEM_ID = "id";
    public static final String COL_EQUIPMENT_ITEM_LIST_ID = "list_id";
    public static final String COL_EQUIPMENT_ITEM_NAME = "item_name";
    public static final String COL_EQUIPMENT_ITEM_CATEGORY = "category";
    public static final String COL_EQUIPMENT_ITEM_QUANTITY = "quantity";
    public static final String COL_EQUIPMENT_ITEM_PACKED = "packed";

    // ============================
    // TABLE JOURNAL ENTRIES
    // ============================
    public static final String TABLE_JOURNAL_ENTRIES = "journal_entries";
    public static final String COL_JOURNAL_ID = "id";
    public static final String COL_JOURNAL_TRIP_ID = "trip_id";
    public static final String COL_JOURNAL_TITLE = "title";
    public static final String COL_JOURNAL_CONTENT = "content";
    public static final String COL_JOURNAL_DATE = "date";
    public static final String COL_JOURNAL_LOCATION = "location";
    public static final String COL_JOURNAL_PHOTO_PATH = "photo_path";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // IMPORTANT : activer les FOREIGN KEY
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USER_FIRST_NAME + " TEXT NOT NULL, "
                + COL_USER_LAST_NAME + " TEXT NOT NULL, "
                + COL_USER_EMAIL + " TEXT UNIQUE NOT NULL, "
                + COL_USER_PASSWORD + " TEXT NOT NULL, "
                + COL_USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")");

        // TRIPS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TRIPS + " ("
                + COL_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TRIP_USER_ID + " INTEGER NOT NULL, "
                + COL_TRIP_NAME + " TEXT NOT NULL, "
                + COL_TRIP_LOCATION + " TEXT NOT NULL, "
                + COL_TRIP_START_DATE + " TEXT NOT NULL, "
                + COL_TRIP_END_DATE + " TEXT NOT NULL, "
                + COL_TRIP_PARTICIPANTS + " INTEGER DEFAULT 1, "
                + COL_TRIP_STATUS + " TEXT DEFAULT 'planifié', "
                + COL_TRIP_BUDGET + " REAL DEFAULT 0, "
                + COL_TRIP_DESCRIPTION + " TEXT, "
                + COL_TRIP_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY(" + COL_TRIP_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ") ON DELETE CASCADE"
                + ")");

        // ACTIVITIES
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITIES + " ("
                + COL_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ACTIVITY_TRIP_ID + " INTEGER NOT NULL, "
                + COL_ACTIVITY_NAME + " TEXT NOT NULL, "
                + COL_ACTIVITY_DATE + " TEXT NOT NULL, "
                + COL_ACTIVITY_TIME + " TEXT, "
                + COL_ACTIVITY_LOCATION + " TEXT, "
                + COL_ACTIVITY_DESCRIPTION + " TEXT, "
                + COL_ACTIVITY_COMPLETED + " INTEGER DEFAULT 0, "
                + "FOREIGN KEY(" + COL_ACTIVITY_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COL_TRIP_ID + ") ON DELETE CASCADE"
                + ")");

        // BUDGET CATEGORIES
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BUDGET_CATEGORIES + " ("
                + COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CATEGORY_TRIP_ID + " INTEGER NOT NULL, "
                + COL_CATEGORY_NAME + " TEXT NOT NULL, "
                + COL_CATEGORY_BUDGET + " REAL DEFAULT 0, "
                + COL_CATEGORY_SPENT + " REAL DEFAULT 0, "
                + COL_CATEGORY_COLOR + " TEXT, "
                + "FOREIGN KEY(" + COL_CATEGORY_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COL_TRIP_ID + ") ON DELETE CASCADE"
                + ")");

        // TRANSACTIONS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTIONS + " ("
                + COL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TRANSACTION_CATEGORY_ID + " INTEGER NOT NULL, "
                + COL_TRANSACTION_DESCRIPTION + " TEXT NOT NULL, "
                + COL_TRANSACTION_AMOUNT + " REAL NOT NULL, "
                + COL_TRANSACTION_DATE + " TEXT NOT NULL, "
                + COL_TRANSACTION_TYPE + " TEXT DEFAULT 'dépense', "
                + "FOREIGN KEY(" + COL_TRANSACTION_CATEGORY_ID + ") REFERENCES " + TABLE_BUDGET_CATEGORIES + "(" + COL_CATEGORY_ID + ") ON DELETE CASCADE"
                + ")");

        // RESERVATIONS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RESERVATIONS + " ("
                + COL_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_RESERVATION_TRIP_ID + " INTEGER NOT NULL, "
                + COL_RESERVATION_NAME + " TEXT NOT NULL, "
                + COL_RESERVATION_TYPE + " TEXT NOT NULL, "
                + COL_RESERVATION_DATE + " TEXT NOT NULL, "
                + COL_RESERVATION_PRICE + " REAL DEFAULT 0, "
                + COL_RESERVATION_STATUS + " TEXT DEFAULT 'confirmé', "
                + COL_RESERVATION_CONFIRMATION + " TEXT, "
                + "FOREIGN KEY(" + COL_RESERVATION_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COL_TRIP_ID + ") ON DELETE CASCADE"
                + ")");

        // EQUIPMENT LISTS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EQUIPMENT_LISTS + " ("
                + COL_EQUIPMENT_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EQUIPMENT_LIST_TRIP_ID + " INTEGER NOT NULL, "
                + COL_EQUIPMENT_LIST_NAME + " TEXT NOT NULL, "
                + COL_EQUIPMENT_LIST_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY(" + COL_EQUIPMENT_LIST_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COL_TRIP_ID + ") ON DELETE CASCADE"
                + ")");

        // EQUIPMENT ITEMS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EQUIPMENT_ITEMS + " ("
                + COL_EQUIPMENT_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EQUIPMENT_ITEM_LIST_ID + " INTEGER NOT NULL, "
                + COL_EQUIPMENT_ITEM_NAME + " TEXT NOT NULL, "
                + COL_EQUIPMENT_ITEM_CATEGORY + " TEXT, "
                + COL_EQUIPMENT_ITEM_QUANTITY + " INTEGER DEFAULT 1, "
                + COL_EQUIPMENT_ITEM_PACKED + " INTEGER DEFAULT 0, "
                + "FOREIGN KEY(" + COL_EQUIPMENT_ITEM_LIST_ID + ") REFERENCES " + TABLE_EQUIPMENT_LISTS + "(" + COL_EQUIPMENT_LIST_ID + ") ON DELETE CASCADE"
                + ")");

        // JOURNAL ENTRIES
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_JOURNAL_ENTRIES + " ("
                + COL_JOURNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_JOURNAL_TRIP_ID + " INTEGER NOT NULL, "
                + COL_JOURNAL_TITLE + " TEXT NOT NULL, "
                + COL_JOURNAL_CONTENT + " TEXT, "
                + COL_JOURNAL_DATE + " TEXT NOT NULL, "
                + COL_JOURNAL_LOCATION + " TEXT, "
                + COL_JOURNAL_PHOTO_PATH + " TEXT, "
                + "FOREIGN KEY(" + COL_JOURNAL_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COL_TRIP_ID + ") ON DELETE CASCADE"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL_ENTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }
}