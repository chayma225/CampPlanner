// ==================== Constants.java ====================
package com.campplanner.app.utils;

public class Constants {

    // Trip Status
    public static final String STATUS_PLANNED = "planifié";
    public static final String STATUS_CONFIRMED = "confirmé";
    public static final String STATUS_IN_PROGRESS = "en cours";
    public static final String STATUS_COMPLETED = "terminé";

    // Budget Categories
    public static final String CATEGORY_ACCOMMODATION = "Hébergement";
    public static final String CATEGORY_FOOD = "Nourriture";
    public static final String CATEGORY_TRANSPORT = "Transport";
    public static final String CATEGORY_ACTIVITIES = "Activités";
    public static final String CATEGORY_EQUIPMENT = "Équipement";
    public static final String CATEGORY_MISCELLANEOUS = "Divers";

    // Budget Category Colors
    public static final String COLOR_ACCOMMODATION = "#3498DB";
    public static final String COLOR_FOOD = "#2ECC71";
    public static final String COLOR_TRANSPORT = "#F39C12";
    public static final String COLOR_ACTIVITIES = "#9B59B6";
    public static final String COLOR_EQUIPMENT = "#E74C3C";
    public static final String COLOR_MISCELLANEOUS = "#95A5A6";

    // Equipment Categories
    public static final String EQUIPMENT_SHELTER = "Abri";
    public static final String EQUIPMENT_SLEEPING = "Couchage";
    public static final String EQUIPMENT_COOKING = "Cuisine";
    public static final String EQUIPMENT_CLOTHING = "Vêtements";
    public static final String EQUIPMENT_ESSENTIAL = "Essentiel";

    // Reservation Types
    public static final String RESERVATION_CAMPSITE = "Emplacement";
    public static final String RESERVATION_ACTIVITY = "Activité";
    public static final String RESERVATION_TRANSPORT = "Transport";

    // Reservation Status
    public static final String RESERVATION_CONFIRMED = "confirmé";
    public static final String RESERVATION_PENDING = "en attente";
    public static final String RESERVATION_CANCELLED = "annulé";

    // Date Formats
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";

    // Request Codes
    public static final int REQUEST_IMAGE_CAPTURE = 1001;
    public static final int REQUEST_IMAGE_PICK = 1002;
    public static final int REQUEST_LOCATION = 1003;

    // Intent Keys
    public static final String KEY_TRIP_ID = "trip_id";
    public static final String KEY_ACTIVITY_ID = "activity_id";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_LIST_ID = "list_id";
    public static final String KEY_ENTRY_ID = "entry_id";

    // Validation
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_TRIP_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
}