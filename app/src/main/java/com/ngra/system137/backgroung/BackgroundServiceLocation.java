package com.ngra.system137.backgroung;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class BackgroundServiceLocation extends Service {

    public static StringBuilder ServiceResult;
    public static GetResult getResult;
    private LatLng negra;
    private String JobType;

    public interface GetResult {//__________________________________________________________________ Start GetResult
        void GetAddress();
    }//_____________________________________________________________________________________________ End GetResult


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        JobType = intent.getStringExtra("jobtype");
        double lat = intent.getExtras().getDouble("lat");
        double log = intent.getExtras().getDouble("log");
        negra = new LatLng(lat, log);
        GetAddressFromLatLong();
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                switch (JobType) {
//                    case "TextAddress":
//                        double lat = intent.getExtras().getDouble("lat");
//                        double log = intent.getExtras().getDouble("log");
//                        negra = new LatLng(lat, log);
//                        GetAddressFromLatLong();
//                        break;
//                }
//            }
//        }, 2000);

        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private void GetAddressFromLatLong(){//_________________________________________________________ Start GetAddressFromLatLong


        String LongAddress = "";
        try {

            Geocoder geocoder;
            List<Address> addresses;
            Locale locale = new Locale("fa_IR");
            Locale.setDefault(locale);
            geocoder = new Geocoder(this, locale);
            addresses = geocoder.getFromLocation(negra.latitude, negra.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if (addresses.size() == 0) {
                getResult.GetAddress();
            } else {
                Address LongPosition = addresses.get(0);
                for (Address longAddress : addresses) {
                    String ad = longAddress.getAddressLine(0);
                    if (ad.length() > LongAddress.length()) {
                        LongAddress = ad;
                        LongPosition = longAddress;
                    }
                }
                //String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = LongPosition.getLocality();
                String state = LongPosition.getAdminArea();
                String country = LongPosition.getCountryName();
                String SubLocality = LongPosition.getSubLocality();
                String knownName = LongPosition.getFeatureName();
                String thoroughfare = LongPosition.getThoroughfare();
                String Sunthoroughfare = LongPosition.getSubThoroughfare();
                ServiceResult = new StringBuilder();
                ServiceResult.append("");
                if ((country != null) && (!country.equalsIgnoreCase("null"))) {
                    ServiceResult.append(country);
                    ServiceResult.append(" ");
                }

                if ((state != null) && (!state.equalsIgnoreCase("null"))) {
                    ServiceResult.append(state);
                    ServiceResult.append(" ");
                }

                if ((city != null) && (!city.equalsIgnoreCase("null"))) {
                    ServiceResult.append(city);
                    ServiceResult.append(" ");
                }

                if ((thoroughfare != null) && (!thoroughfare.equalsIgnoreCase("null"))) {
                    ServiceResult.append(thoroughfare);
                    ServiceResult.append(" ");
                }

                if ((Sunthoroughfare != null) && (!Sunthoroughfare.equalsIgnoreCase("null"))) {
                    ServiceResult.append(Sunthoroughfare);
                    ServiceResult.append(" ");
                }

                if ((SubLocality != null) && (!SubLocality.equalsIgnoreCase("null"))) {
                    ServiceResult.append(SubLocality);
                    ServiceResult.append(" ");
                }
                if ((knownName != null) &&
                        (!knownName.equalsIgnoreCase("null"))
                        && (!knownName.equalsIgnoreCase(thoroughfare)))
                    ServiceResult.append(knownName);
                getResult.GetAddress();
            }
        } catch (IOException e) {
            e.printStackTrace();
            getResult.GetAddress();
        }
    }//_____________________________________________________________________________________________ End GetAddressFromLatLong

}
