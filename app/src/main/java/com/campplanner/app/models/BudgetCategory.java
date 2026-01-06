// ==================== BudgetCategory.java ====================
package com.campplanner.app.models;

public class BudgetCategory {
    private int id;
    private int tripId;
    private String name;
    private double budget;
    private double spent;
    private String color;

    public BudgetCategory() {}

    public BudgetCategory(int id, int tripId, String name, double budget, double spent, String color) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.budget = budget;
        this.spent = spent;
        this.color = color;
    }

    public int getId() { return id; }
    public int getTripId() { return tripId; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
    public double getSpent() { return spent; }
    public String getColor() { return color; }

    public void setId(int id) { this.id = id; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public void setName(String name) { this.name = name; }
    public void setBudget(double budget) { this.budget = budget; }
    public void setSpent(double spent) { this.spent = spent; }
    public void setColor(String color) { this.color = color; }

    public double getRemaining() {
        return budget - spent;
    }

    public double getPercentageUsed() {
        if (budget == 0) return 0;
        return (spent / budget) * 100;
    }
}