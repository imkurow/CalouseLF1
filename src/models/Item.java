package models;

public class Item {
    private String itemId;
    private String sellerId;
    private String name;
    private String category;
    private String size;
    private double price;
    private String status;
    
    public Item(String itemId, String sellerId, String name, String category, String size, double price, String status) {
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.name = name;
        this.category = category;
        this.size = size;
        this.price = price;
        this.status = status;
    }
    
    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
