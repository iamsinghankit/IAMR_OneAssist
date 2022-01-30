package com.onehack.oneassist.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onehack.oneassist.scrap.OfferDetail;

/**
 * @author ankit
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferResponse {
	List<OfferDetail> offerDetails;
	List<String> location;

	/**
	 * @param offerDetails
	 */
	public OfferResponse(List<OfferDetail> offerDetails) {
		super();
		this.offerDetails = offerDetails;
	}

	/**
	 * 
	 */

	public List<OfferDetail> getOfferDetails() {
		return offerDetails;
	}

	/**
	 * @param offerDetails
	 * @param location
	 */
	public OfferResponse(List<OfferDetail> offerDetails, List<String> location) {
		super();
		this.offerDetails = offerDetails;
		this.location = location;
	}

	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public void setOfferDetails(List<OfferDetail> offerDetails) {
		this.offerDetails = offerDetails;
	}

}
