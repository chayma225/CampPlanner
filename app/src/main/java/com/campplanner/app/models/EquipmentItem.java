// ==================== EquipmentItem.java ====================
package com.campplanner.app.models;

public class EquipmentItem {
    private int id;
    private int listId;
    private String name;
    private String category;
    private int quantity;
    private boolean packed;

    public EquipmentItem() {}

    public EquipmentItem(int id, int listId, String name, String category, int quantity, boolean packed) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.packed = packed;
    }public EquipmentItem(String name, String category, int quantity) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.packed = false;
    }

    public int getId() { return id; }
    public int getListId() { return listId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public boolean isPacked() { return packed; }

    public void setId(int id) { this.id = id; }
    public void setListId(int listId) { this.listId = listId; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPacked(boolean packed) { this.packed = packed; }
}