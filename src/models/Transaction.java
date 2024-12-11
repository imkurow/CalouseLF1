package models;

public class Transaction {
	private String transactionId;
    private String userId;
    private String itemId;
    
    public Transaction(String transactionId, String userId, String itemId) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
    }
}
