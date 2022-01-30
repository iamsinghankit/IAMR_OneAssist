package com.onehack.oneassist.scrap;

import org.springframework.stereotype.Component;

/**
 * @author ankit
 *
 */
@Component
public class OfferDetail implements Cloneable {
	private String validity;
	private String restaurantName;
	private String actualOffer;
	private String url;
	private String details;
	private String restaurantDetail;

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getActualOffer() {
		return actualOffer;
	}

	public void setActualOffer(String actualOffer) {
		this.actualOffer = actualOffer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getRestaurantDetail() {
		return restaurantDetail;
	}

	public void setRestaurantDetail(String restaurantDetail) {
		this.restaurantDetail = restaurantDetail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public OfferDetail clone() throws CloneNotSupportedException {
		return (OfferDetail) super.clone();
	}
}
