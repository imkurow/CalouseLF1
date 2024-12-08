package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public class UserController{
	private Connection conn;
	
	//VALIDATION METHODS
	 private boolean validateUsername(String username) {
	        return username.length() >= 3;
	 }
	 //password
	 private boolean validatePassword(String password) {
	        if(password.length() < 8) return false;
	        String specialChars = "!@#$%^&*";
	        for(char c : password.toCharArray()) {
	            if(specialChars.indexOf(c) != -1) return true;
	        }
	        return false;
	 }
	 //phone numnber
	 private boolean validatePhoneNumber(String phone) {
	        return phone.startsWith("+62") && phone.length() >= 11;
	 }
	 
	 private String generateUserId() {
		 String query = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";
		 try {
	            PreparedStatement ps = conn.prepareStatement(query);
	            ResultSet rs = ps.executeQuery();
	            
	            if(rs.next()) {
	                //US001, US002, dst
	                String lastId = rs.getString("user_id");
	                int number = Integer.parseInt(lastId.substring(2)) + 1;
	                return String.format("US%03d", number);
	            } else {
	            	//if there's no user
	                return "US001";
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "US" + System.currentTimeMillis() % 1000;
	        }
	 }
	 
	 //REGISTER
	 public boolean register(String username, String password, String phoneNumber, String address, String role) {
		 //validate
		 if (!validateUsername(username) || !validatePassword(password) || !validatePhoneNumber(phoneNumber) || address.isEmpty()) {
			 return false;
		 }
		 
		 String userId = generateUserId();
		 String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";
		 try {
	            PreparedStatement ps = conn.prepareStatement(query);
	            ps.setString(1, userId);
	            ps.setString(2, username);
	            ps.setString(3, password);
	            ps.setString(4, phoneNumber);
	            ps.setString(5, address);
	            ps.setString(6, role);
	            
	            return ps.executeUpdate() == 1;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	 }
	 
	 //LOGIN	
	 public User login(String username, String password) {
		 String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	        try {
	            PreparedStatement ps = conn.prepareStatement(query);
	            ps.setString(1, username);
	            ps.setString(2, password);
	            
	            ResultSet rs = ps.executeQuery();
	            if(rs.next()) {
	                return new User(
	                    rs.getString("user_id"),
	                    rs.getString("username"),
	                    rs.getString("password"),
	                    rs.getString("phone_number"),
	                    rs.getString("address"),
	                    rs.getString("role")
	                );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	 }
	 
	 

	
	
}