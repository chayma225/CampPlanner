// ==================== Trip.java ====================
package com.campplanner.app.models;

public class Trip {
    private int id;
    private int userId;
    private String name;
    private String location;
    private String startDate;
    private String endDate;
    private int participants;
    private String status;
    private double budget;
    private String description;
    private String createdAt;

    public Trip() {}

    public Trip(int id, int userId, String name, String location, String startDate,
                String endDate, int participants, String status, double budget,
                String description, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = participants;
        this.status = status;
        this.budget = budget;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public int getParticipants() { return participants; }
    public String getStatus() { return status; }
    public double getBudget() { return budget; }
    public String getDescription() { return description; }
    public String getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setParticipants(int participants) { this.participants = participants; }
    public void setStatus(String status) { this.status = status; }
    public void setBudget(double budget) { this.budget = budget; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

}