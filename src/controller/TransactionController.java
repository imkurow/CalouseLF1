package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.Database;
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
        String query = "SELECT * FROM transactions WHERE user_id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
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
    
    
}
