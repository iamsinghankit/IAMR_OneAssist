package offerapp.oneassist.com.oneassisthackathon.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by imran.khan on 09/12/17.
 */

public class CityVo {
    @SerializedName("cityName")
    private String cityName;
    public CityVo(String cityName){
        this.cityName=cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "CityVo{" +
                "cityName='" + cityName + '\'' +
                '}';
    }
}
