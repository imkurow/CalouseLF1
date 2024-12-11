package controller;

import java.sql.Connection;

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
}
