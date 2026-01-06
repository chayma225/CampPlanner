// ==================== JournalEntry.java ====================
package com.campplanner.app.models;

public class JournalEntry {
    private int id;
    private int tripId;
    private String title;
    private String content;
    private String date;
    private String location;
    private String photoPath;

    public JournalEntry() {}

    public JournalEntry(int id, int tripId, String title, String content,
                        String date, String location, String photoPath) {
        this.id = id;
        this.tripId = tripId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.location = location;
        this.photoPath = photoPath;
    }

    public int getId() { return id; }
    public int getTripId() { return tripId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getPhotoPath() { return photoPath; }

    public void setId(int id) { this.id = id; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}