package com.onehack.oneassist.response;

import java.util.List;

/**
 * @author ankit
 *
 */
public class CityResponse {
	private List<String> cities;

	/**
	 * @param cities
	 */
	public CityResponse(List<String> cities) {
		super();
		this.cities = cities;
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

}
