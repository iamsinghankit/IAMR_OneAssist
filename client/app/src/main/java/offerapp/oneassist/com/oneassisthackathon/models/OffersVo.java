package offerapp.oneassist.com.oneassisthackathon.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by imran.khan on 09/12/17.
 */

public class OffersVo {
    @SerializedName("validity")
    private String validity;

    @Override
    public String toString() {
        return "OffersVo{" +
                "validity='" + validity + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", actualOffer='" + actualOffer + '\'' +
                '}';
    }

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

    @SerializedName("restaurantName")
    private String restaurantName;
    @SerializedName("actualOffer")
    private String actualOffer;

    public String getRestaurantDetail() {
        return restaurantDetail;
    }

    public void setRestaurantDetail(String restaurantDetail) {
        this.restaurantDetail = restaurantDetail;
    }

    @SerializedName("restaurantDetail")
    private String restaurantDetail;

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

    @SerializedName("url")
    private String url;
    @SerializedName("details")
    private String details;

    public OffersVo(String validity,String restaurantName,String actualOffer,String url,String details,String restaurantDetail){
        this.validity=validity;
        this.restaurantName=restaurantName;
        this.actualOffer=actualOffer;
        this.url=url;
        this.details=details;
        this.restaurantDetail=restaurantDetail;
    }


}
