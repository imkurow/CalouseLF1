package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.Database;
import models.Item;
import models.Transaction;


public class TransactionController {
	private Connection conn;
	private ItemController itemController;
    
    public TransactionController() {
        try {
            this.conn = Database.getInstance().getConnection();
            this.itemController = new ItemController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String generateTransactionId() {
        String query = "SELECT transaction_id FROM transactions ORDER BY transaction_id DESC LIMIT 1";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                String lastId = rs.getString("transaction_id");
                int number = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("TR%03d", number);
            }
            return "TR001";
        } catch (SQLException e) {
            e.printStackTrace();
            return "TR" + System.currentTimeMillis() % 1000;
        }
    }
    
    public boolean createTransaction(String userId, String itemId) {
        String transactionId = generateTransactionId();
        
        try {
            conn.setAutoCommit(false);
            
            // Insert transaction
            String insertQuery = "INSERT INTO transactions (transaction_id, user_id, item_id) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, transactionId);
            ps.setString(2, userId);
            ps.setString(3, itemId);
            
            int result = ps.executeUpdate();
            
            if(result == 1) {
//                wishlistController.removeFromAllWishlists(itemId);

                // daripada dihapus, maka diubah statusnya menjadi sold
                // itemController.deleteItem(itemId);
                
                String updateQuery = "UPDATE items SET item_status = 'sold' WHERE item_id = ?";
                PreparedStatement updatePs = conn.prepareStatement(updateQuery);
                updatePs.setString(1, itemId);
                updatePs.executeUpdate();
                
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<Transaction> getPurchaseHistory(String userId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.transaction_id, t.user_id, t.item_id, o.offer_price " +
                      "FROM transactions t " +
                      "LEFT JOIN offers o ON t.item_id = o.item_id AND t.user_id = o.user_id " +
                      "AND o.offer_status = 'accepted' " +
                      "WHERE t.user_id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = itemController.getItemById(rs.getString("item_id"));
                if(item != null) {
                    double finalPrice = rs.getObject("offer_price") != null ? 
                                      rs.getDouble("offer_price") : 
                                      item.getPrice();
                    item.setPrice(finalPrice); 
                }
                
                Transaction transaction = new Transaction(
                    rs.getString("transaction_id"),
                    rs.getString("user_id"),
                    rs.getString("item_id")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    public double getTransactionFinalPrice(String transactionId, String itemId, String userId) {
        String query = "SELECT o.offer_price " +
                      "FROM transactions t " +
                      "LEFT JOIN offers o ON t.item_id = o.item_id AND t.user_id = o.user_id " +
                      "WHERE t.transaction_id = ? AND o.offer_status = 'accepted'";
                      
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, transactionId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next() && rs.getObject("offer_price") != null) {
                // Jika ada harga penawaran yang diterima
                return rs.getDouble("offer_price");
            } else {
                // Jika tidak ada penawaran (pembelian langsung), ambil harga asli
                Item item = itemController.getItemById(itemId);
                return item != null ? item.getPrice() : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    
}
