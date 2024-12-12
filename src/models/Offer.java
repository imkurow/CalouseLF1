package models;

public class Offer {
	private String offerId;
    private String userId;
    private String itemId;
    private double offerPrice;
    private String offerStatus;
    
    public Offer(String offerId, String userId, String itemId, double offerPrice, String offerStatus) {
        this.offerId = offerId;
        this.userId = userId;
        this.itemId = itemId;
        this.offerPrice = offerPrice;
        this.offerStatus = offerStatus;
    }

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
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

	public double getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(double offerPrice) {
		this.offerPrice = offerPrice;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
    
    
    
    
}

