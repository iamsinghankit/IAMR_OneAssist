package com.onehack.oneassist.scrap;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onehack.oneassist.response.OfferResponse;

/**
 * @author ankit
 *
 */
@Service
public class WebReader {
	private OfferDetail offerDetail;

	@Autowired
	public WebReader(OfferDetail offerDetail) {
		this.offerDetail = offerDetail;
	}

	public OfferResponse getOffer(String name) {
		String url = "https://www.online.citibank.co.in/card-offers/credit-card-" + name + ".htm";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			return getOfferDetails(doc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private OfferResponse getOfferDetails(Document doc) throws Exception {
		Elements elements = doc.getAllElements();
		ListIterator<Element> it = elements.listIterator();
		List<OfferDetail> details = new ArrayList<>();
		OfferDetail fakeDetail = null;
		List<String> location = new ArrayList<>();
		while (it.hasNext()) {
			Element element = it.next();
			if (element.hasClass("cusinedrp") && element.getElementById("suburb") != null) {
				//				System.out.println(element.text());
				for (Element e : element.select("option")) {
					if (e.text().equalsIgnoreCase("All"))
						continue;
					location.add(e.text());
				}
				//				location = Arrays.asList(element.text().split(","));
			}

			if (element.hasClass("cusine_hd")) {
				fakeDetail = offerDetail.clone();
				fakeDetail.setDetails(element.ownText());
			}
			if (element.hasClass("validity_hd"))
				fakeDetail.setValidity(element.text().replaceAll("Offer validity:", ""));
			if (element.hasClass("so-bluetext11"))
				fakeDetail.setRestaurantName(element.text());

			if (element.hasClass("socommonorange"))
				fakeDetail.setActualOffer(element.text());

			if (element.hasClass("txt11"))
				fakeDetail.setRestaurantDetail(element.text());

			if (element.hasClass("so-resholderleft")) {
				Element image = element.select("img").first();
				fakeDetail.setUrl(image.absUrl("src"));
				details.add(fakeDetail);
			}
		}
		return new OfferResponse(details, location);
	}

	public OfferResponse filteredByLocation(String city, String location) {
		return new OfferResponse(filtered(getOffer(city).getOfferDetails(), location));
	}

	private List<OfferDetail> filtered(List<OfferDetail> offerDetail, String location) {
		return offerDetail.stream().filter(e -> e.getDetails().contains(location)).collect(Collectors.toList());
	}

}
