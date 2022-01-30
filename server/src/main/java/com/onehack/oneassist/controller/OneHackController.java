package com.onehack.oneassist.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onehack.oneassist.entity.City;
import com.onehack.oneassist.repo.CityRepositoty;
import com.onehack.oneassist.response.CityResponse;
import com.onehack.oneassist.response.OfferResponse;
import com.onehack.oneassist.scrap.WebReader;

/**
 * @author ankit
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/onehack/")
public class OneHackController {
	@Autowired
	private CityRepositoty cityRepo;

	@Autowired
	private WebReader reader;

	@GetMapping(value = "/city")
	public CityResponse getCity() {
		return generateCity();
	}

	@GetMapping(value = "/offer/{city}")
	public OfferResponse getOffer(@PathVariable("city") String city) {
		return reader.getOffer(city);
	}

	private CityResponse generateCity() {
		List<City> cities = (List<City>) cityRepo.findAll();
		return new CityResponse(getCity((cities)));
	}

	@GetMapping(value = "/offer/{city}/{location}")
	public OfferResponse filtered(@PathVariable("city") String city, @PathVariable("location") String location) {
		return reader.filteredByLocation(city, location);
	}

	private List<String> getCity(List<City> cities) {
		return cities.stream().map(City::getCityName).collect(Collectors.toList());
	}

}
