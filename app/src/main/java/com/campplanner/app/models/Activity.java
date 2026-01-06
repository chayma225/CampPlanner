// ==================== Activity.java ====================
package com.campplanner.app.models;

public class Activity {
    private int id;
    private int tripId;
    private String name;
    private String date;
    private String time;
    private String location;
    private String description;
    private boolean completed;

    public Activity() {}

    public Activity(int id, int tripId, String name, String date, String time,
                    String location, String description, boolean completed) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.completed = completed;
    }

    public int getId() { return id; }
    public int getTripId() { return tripId; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }

    public void setId(int id) { this.id = id; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}