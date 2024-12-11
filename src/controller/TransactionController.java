package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.Database;

public class TransactionController {
	private Connection conn;
    
    public TransactionController() {
        try {
            this.conn = Database.getInstance().getConnection();
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
    
    
}
