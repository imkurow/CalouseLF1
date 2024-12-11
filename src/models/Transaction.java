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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
    
    
}
