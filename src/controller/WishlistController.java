package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.Database;
import models.Item;

public class WishlistController {

	private Connection conn;
    private ItemController itemController;
    
    public WishlistController() {
        try {
            this.conn = Database.getInstance().getConnection();
            this.itemController = new ItemController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String generateWishlistId() {
        String query = "SELECT wishlist_id FROM wishlists ORDER BY wishlist_id DESC LIMIT 1";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String lastId = rs.getString("wishlist_id");
                int number = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("WL%03d", number);
            }
            return "WL001";
        } catch (SQLException e) {
            e.printStackTrace();
            return "WL" + System.currentTimeMillis() % 1000;
        }
    }
    
    public boolean addToWishlist(String userId, String itemId) {
        if(isItemInWishlist(userId, itemId)) return false;
        
        String wishlistId = generateWishlistId();
        String query = "INSERT INTO wishlists (wishlist_id, user_id, item_id) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, wishlistId);
            ps.setString(2, userId);
            ps.setString(3, itemId);
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isItemInWishlist(String userId, String itemId) {
        String query = "SELECT * FROM wishlists WHERE user_id = ? AND item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, itemId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Item> getWishlistItems(String userId) {
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT i.* FROM items i JOIN wishlists w ON i.item_id = w.item_id WHERE w.user_id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userId);
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
    
    public boolean removeFromWishlist(String userId, String itemId) {
        String query = "DELETE FROM wishlists WHERE user_id = ? AND item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, itemId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
