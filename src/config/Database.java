package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	private static final String URL = "jdbc:mysql://localhost:3306/calouself";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Database instance = null;
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Database getInstance() {
    	if(instance == null) {
    		instance = new Database();
    	}
    	return instance;
    }
}
