package com.ngra.system137.backgroung;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AsyncGeocoder extends AsyncTask<String, String, String> {

    public static StringBuilder ServiceResult;
    private LatLng negra;
    private Context context;

    public AsyncGeocoder(LatLng negra, Context context) {
        this.negra = negra;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String LongAddress = "";
        try {
            Geocoder geocoder;
            List<Address> addresses;
            Locale locale = new Locale("fa_IR");
            Locale.setDefault(locale);
            geocoder = new Geocoder(context, locale);
            addresses = geocoder.getFromLocation(negra.latitude, negra.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if (addresses.size() == 0) {
                //getResult.GetAddress();
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
                //getResult.GetAddress();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //getResult.GetAddress();
        }
        return null;
    }
}