package offerapp.oneassist.com.oneassisthackathon.networking.apiresponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by imran.khan on 09/12/17.
 */

public class OffersResponse {

    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("restaurantName")
    @Expose
    private String restaurantName;
    @SerializedName("actualOffer")
    @Expose
    private String actualOffer;

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

}
