// ==================== EquipmentList.java ====================
package com.campplanner.app.models;

public class EquipmentList {
    private int id;
    private int tripId;
    private String name;
    private String createdAt;

    public EquipmentList() {}

    public EquipmentList(int id, int tripId, String name, String createdAt) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getTripId() { return tripId; }
    public String getName() { return name; }
    public String getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public void setName(String name) { this.name = name; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}