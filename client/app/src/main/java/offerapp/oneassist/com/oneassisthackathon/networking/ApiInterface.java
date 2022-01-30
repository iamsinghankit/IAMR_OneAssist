package offerapp.oneassist.com.oneassisthackathon.networking;

import org.json.JSONArray;

import offerapp.oneassist.com.oneassisthackathon.networking.apiresponses.CityResponse;
import offerapp.oneassist.com.oneassisthackathon.networking.apiresponses.OffersResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("city")
    Call<ResponseBody> getAllCities();

    @GET("offer/{cityname}")
    Call<ResponseBody> getCityDetails(@Path("cityname") String cityname);
    @GET("offer/{cityname}/{localname}")
    Call<ResponseBody> getCityDetailsLocalty(@Path("cityname") String cityname,@Path("localname") String localname);
}