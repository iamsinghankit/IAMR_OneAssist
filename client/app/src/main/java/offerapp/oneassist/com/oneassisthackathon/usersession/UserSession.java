package offerapp.oneassist.com.oneassisthackathon.usersession;

import java.util.ArrayList;

import offerapp.oneassist.com.oneassisthackathon.models.CityVo;
import offerapp.oneassist.com.oneassisthackathon.models.OffersVo;

/**
 * Created by imran.khan on 09/12/17.
 */

public class UserSession {
    private static UserSession mInstance = null;
    private ArrayList<CityVo> cityVoArrayList;
    private ArrayList<OffersVo> offersVoArrayList;

    public ArrayList<String> getStringArrayLocaltyList() {
        return stringArrayLocaltyList;
    }

    public void setStringArrayLocaltyList(ArrayList<String> stringArrayLocaltyList) {
        this.stringArrayLocaltyList = stringArrayLocaltyList;
    }

    private ArrayList<String> stringArrayLocaltyList;
    public static UserSession getInstance(){
        if(mInstance == null)
        {
            mInstance = new UserSession();
        }
        return mInstance;
    }
    public ArrayList<OffersVo> getOffersVoArrayList() {
        return offersVoArrayList;
    }

    public void setOffersVoArrayList(ArrayList<OffersVo> offersVoArrayList) {
        this.offersVoArrayList = offersVoArrayList;
    }
    public ArrayList<CityVo> getCityVoArrayList() {
        return cityVoArrayList;
    }

    public void setCityVoArrayList(ArrayList<CityVo> cityVoArrayList) {
        this.offersVoArrayList = offersVoArrayList;
    }
}
