package offerapp.oneassist.com.oneassisthackathon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import offerapp.oneassist.com.oneassisthackathon.models.CityVo;
import offerapp.oneassist.com.oneassisthackathon.models.OffersVo;
import offerapp.oneassist.com.oneassisthackathon.networking.ApiClient;
import offerapp.oneassist.com.oneassisthackathon.networking.ApiInterface;
import offerapp.oneassist.com.oneassisthackathon.networking.apiresponses.CityResponse;
import offerapp.oneassist.com.oneassisthackathon.permissions.PermissionsActivity;
import offerapp.oneassist.com.oneassisthackathon.usersession.UserSession;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String STR_TAG=SplashActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getAllCities();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //List<CityVo> movies = response.body().getCities();
                try {
                    String strResponse=response.body().string();
                    Log.d(STR_TAG, "Number of movies received: " + strResponse);
                    JSONObject jsonObject=new JSONObject(strResponse);
                    JSONArray jsonArray=jsonObject.getJSONArray("cities");
                    ArrayList<CityVo> cityVoArrayList=new ArrayList<>();
                    for (int count=0;count<jsonArray.length();count++){
                        cityVoArrayList.add(new CityVo(jsonArray.optString(count)));
                    }
                    UserSession.getInstance().setCityVoArrayList(cityVoArrayList);

                    getAllOffersforCity("delhi");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Log.e(STR_TAG, t.toString());
            }
        });

    }

    private void getAllOffersforCity(String strCity){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getCityDetails(strCity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //List<CityVo> movies = response.body().getCities();
                try{
                    String strResponse=response.body().string();
                    JSONObject jsonObject=new JSONObject(strResponse);
                    Log.d(STR_TAG, "Number of movies received: " + strResponse);
                    JSONArray jsonArray=jsonObject.getJSONArray("offerDetails");
                    ArrayList<OffersVo> offersVoArrayList=new ArrayList<>();
                    for (int count=0;count<jsonArray.length();count++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(count);
                        offersVoArrayList.add(new OffersVo(jsonObject1.optString("validity"),
                                jsonObject1.optString("restaurantName"),
                                jsonObject1.optString("actualOffer"),
                                jsonObject1.optString("url"),
                                jsonObject1.optString("details"),
                                jsonObject1.optString("restaurantDetail")));
                    }
                    UserSession.getInstance().setOffersVoArrayList(offersVoArrayList);

                    JSONArray jsonArrayLocalty=jsonObject.getJSONArray("location");
                    ArrayList<String> arrayList=new ArrayList<>();
                    for (int count=0;count<jsonArrayLocalty.length();count++){
                        String localtyName=jsonArrayLocalty.optString(count);
                        arrayList.add(localtyName);
                    }
                    UserSession.getInstance().setStringArrayLocaltyList(arrayList);
                    Log.d(STR_TAG, "Number of movies received: " + response.body().string());
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        Intent intent =new Intent(SplashActivity.this,PermissionsActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent =new Intent(SplashActivity.this,Dashboard.class);
                        startActivity(intent);
                    }

                    SplashActivity.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Log.e(STR_TAG, t.toString());
            }
        });
    }

}
