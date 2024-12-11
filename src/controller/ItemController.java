package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.Database;
import models.Item;

public class ItemController {
	private Connection conn;
	
	public ItemController() {
		try {
            this.conn = Database.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private String generateItemId() {
        String query = "SELECT item_id FROM items ORDER BY item_id DESC LIMIT 1";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String lastId = rs.getString("item_id");
                int number = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("IT%03d", number);
            }
            return "IT001"; 
        } catch (SQLException e) {
            e.printStackTrace();
            return "IT" + System.currentTimeMillis() % 1000;
        }
    }
	
	public boolean validateItem(String name, String category, String size, double price) {
		if(name.length() < 3 || name.isEmpty()) return false;
        if(category.length() < 3 || category.isEmpty()) return false;
        if(size.isEmpty()) return false;
        if(price <= 0) return false;
        return true;
	}
	
	public boolean uploadItem(String sellerId, String name, String category, String size, double price) {
	    if(!validateItem(name, category, size, price)) return false;
	    
	    String itemId = generateItemId();
	    String query = "INSERT INTO items (item_id, seller_id, item_name, item_category, item_size, item_price, item_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    
	    try {
	        PreparedStatement ps = conn.prepareStatement(query);
	        
	        ps.setString(1, itemId);
	        ps.setString(2, sellerId);
	        ps.setString(3, name);
	        ps.setString(4, category);
	        ps.setString(5, size);
	        ps.setDouble(6, price);
	        ps.setString(7, "pending"); 
	        
	        return ps.executeUpdate() == 1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public ArrayList<Item> getSellerItems(String sellerId) {
	    ArrayList<Item> items = new ArrayList<>();
	    String query = "SELECT * FROM items WHERE seller_id = ?";
	    
	    try {
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setString(1, sellerId);
	        ResultSet rs = ps.executeQuery();
	        
	        while(rs.next()) {
	            Item item = new Item(
	                rs.getString("item_id"),
	                rs.getString("seller_id"),
	                rs.getString("item_name"),
	                rs.getString("item_category"),
	                rs.getString("item_size"),
	                rs.getDouble("item_price"),
	                rs.getString("item_status")
	            );
	            items.add(item);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return items;
	}
	
}
