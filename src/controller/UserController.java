package controller;

import java.sql.Connection;

public class UserController{
	private Connection conn;
	
	//VALIDATION METHODS
	 private boolean validateUsername(String username) {
	        return username.length() >= 3;
	 }
	
	
}