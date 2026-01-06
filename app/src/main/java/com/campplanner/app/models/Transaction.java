package com.campplanner.app.models;

public class Transaction {
    private int id;
    private int categoryId;
    private String description;
    private double amount;
    private String date;
    private String type;

    public Transaction() {}

    public Transaction(int id, int categoryId, String description, double amount, String date, String type) {
        this.id = id;
        this.categoryId = categoryId;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getId() { return id; }
    public int getCategoryId() { return categoryId; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getType() { return type; }

    public void setId(int id) { this.id = id; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(String date) { this.date = date; }
    public void setType(String type) { this.type = type; }
}