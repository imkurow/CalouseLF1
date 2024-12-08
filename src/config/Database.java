package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	 private static Database instance;
	    private Connection connection;
	    
	    private final String USERNAME = "root";
	    private final String PASSWORD = "";
	    private final String DATABASE = "calouself";
	    private final String HOST = "localhost:3306";
	    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	    
	    private Database() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static Database getInstance() {
	        if (instance == null) {
	            synchronized(Database.class) {
	                if (instance == null) {
	                    instance = new Database();
	                }
	            }
	        }
	        return instance;
	    }
	    
	    public Connection getConnection() {
	        return connection;
	    }
}
