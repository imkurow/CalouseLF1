package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.Database;

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
	
}
