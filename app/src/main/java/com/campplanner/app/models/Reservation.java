// ==================== Reservation.java ====================
package com.campplanner.app.models;

public class Reservation {
    private int id;
    private int tripId;
    private String name;
    private String type;
    private String date;
    private double price;
    private String status;
    private String confirmationNumber;

    public Reservation() {}

    public Reservation(int id, int tripId, String name, String type, String date,
                       double price, String status, String confirmationNumber) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.type = type;
        this.date = date;
        this.price = price;
        this.status = status;
        this.confirmationNumber = confirmationNumber;
    }

    public int getId() { return id; }
    public int getTripId() { return tripId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDate() { return date; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public String getConfirmationNumber() { return confirmationNumber; }

    public void setId(int id) { this.id = id; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDate(String date) { this.date = date; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
}