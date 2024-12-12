package controller;

import java.sql.Connection;

import config.Database;

public class WishlistController {

	private Connection conn;
    private ItemController itemController;
    
    public WishlistController() {
        try {
            this.conn = Database.getInstance().getConnection();
            this.itemController = new ItemController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
