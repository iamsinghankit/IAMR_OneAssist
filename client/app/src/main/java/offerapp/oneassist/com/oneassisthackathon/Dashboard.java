package offerapp.oneassist.com.oneassisthackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import offerapp.oneassist.com.oneassisthackathon.Utils.Utils;
import offerapp.oneassist.com.oneassisthackathon.adapters.OfferAdapter;
import offerapp.oneassist.com.oneassisthackathon.models.CityVo;
import offerapp.oneassist.com.oneassisthackathon.models.OffersVo;
import offerapp.oneassist.com.oneassisthackathon.networking.ApiClient;
import offerapp.oneassist.com.oneassisthackathon.networking.ApiInterface;
import offerapp.oneassist.com.oneassisthackathon.usersession.UserSession;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by imran.khan on 09/12/17.
 */

public class Dashboard extends AppCompatActivity {
    private final String STR_TAG=Dashboard.class.getName();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Spinner spinner_localty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.d("Not","Token ["+tkn+"]");

        setTitle("Home");
        setContentView(R.layout.layout_dashboard);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        OfferAdapter ca = new OfferAdapter(UserSession.getInstance().getOffersVoArrayList());
        recyclerView.setAdapter(ca);
        spinner_localty=(Spinner)findViewById(R.id.spinner_localty);
        prepareLocaltySpinner();
        spinner_localty.setSelection(0,true);
        spinner_localty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                progressDialog=new ProgressDialog(Dashboard.this);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Refereshing....");
                progressDialog.show();
                String selectedItem = parent.getItemAtPosition(position).toString();

                getAllOffersforCityLocalty(spinner.getSelectedItem().toString(),selectedItem);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }


    private void prepareLocaltySpinner(){

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        UserSession.getInstance().getStringArrayLocaltyList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_localty.setAdapter(adapter);
        spinner_localty.setSelection(0,true);
    }



    private Spinner spinner;
    private String prevSelectedItem="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        spinner= (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.citry_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(4,true);
                prevSelectedItem=spinner.getSelectedItem().toString();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                progressDialog=new ProgressDialog(Dashboard.this);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Refereshing....");
                progressDialog.show();
                String selectedItem = parent.getItemAtPosition(position).toString();

                getAllOffersforCity(selectedItem);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        return true;
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
                    recyclerView.setAdapter(null);
                    OfferAdapter ca = new OfferAdapter(UserSession.getInstance().getOffersVoArrayList());
                    recyclerView.setAdapter(ca);
                    prepareLocaltySpinner();
                    progressDialog.dismiss();
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
    private void getAllOffersforCityLocalty(String strCity,String strLocalty){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getCityDetailsLocalty(strCity,strLocalty);
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
                    Log.d(STR_TAG, "Number of movies received: " + response.body().string());
                    recyclerView.setAdapter(null);
                    OfferAdapter ca = new OfferAdapter(UserSession.getInstance().getOffersVoArrayList());
                    recyclerView.setAdapter(ca);
                    progressDialog.dismiss();
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
