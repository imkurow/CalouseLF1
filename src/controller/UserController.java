package controller;

import java.sql.Connection;

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

	
	
}