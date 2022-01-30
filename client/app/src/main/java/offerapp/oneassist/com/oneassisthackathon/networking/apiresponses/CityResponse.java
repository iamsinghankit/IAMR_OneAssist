package offerapp.oneassist.com.oneassisthackathon.networking.apiresponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import offerapp.oneassist.com.oneassisthackathon.models.CityVo;

/**
 * Created by imran.khan on 09/12/17.
 */

public class CityResponse {

    @SerializedName("cities")
    @Expose
    private List<CityVo> cities = null;

    public List<CityVo> getCities() {
        return cities;
    }

    public void setCities(List<CityVo> cities) {
        this.cities = cities;
    }

}

