package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.util.Log;

import com.ngra.system137.dagger.retrofit.ModelToken;
import com.ngra.system137.dagger.retrofit.RetrofitComponent;
import com.ngra.system137.models.ModelGetAddress;
import com.ngra.system137.views.application.System137;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_MapFragment {

    private Context context;
    private PublishSubject<String> Observables;
    public static ModelGetAddress address;

    public VM_MapFragment(Context context) {//______________________________________________________ Start VM_MapFragment
        this.context = context;
        Observables = PublishSubject.create();
        address = null;
    }//_____________________________________________________________________________________________ End VM_MapFragment



    public void GetAddress(double lat, double lon) {//____________________________________________________________________ Start GetAddress

        RetrofitComponent retrofitComponent = System137
                .getApplication(context)
                .getRetrofitComponent();

        String url = "/reverse?format=json&lat=" + lat + "&lon="+lon+"&zoom=22&addressdetails=5";

        retrofitComponent
                .getRetrofitApiInterface()
                .getAddress(url)
                .enqueue(new Callback<ModelGetAddress>() {
                    @Override
                    public void onResponse(Call<ModelGetAddress> call, Response<ModelGetAddress> response) {
                        address = response.body();
                        if(address.getAddress() == null){
                            address.setLat(String.valueOf(lat));
                            address.setLon(String.valueOf(lon));
                        }
                        Observables.onNext("GetAddress");
                    }

                    @Override
                    public void onFailure(Call<ModelGetAddress> call, Throwable t) {
                        address = new ModelGetAddress();
                        address.setLat(String.valueOf(lat));
                        address.setLon(String.valueOf(lon));
                        Observables.onNext("onFailure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetAddress


    //______________________________________________________________________________________________ Start getObservables
    public PublishSubject<String> getObservables() {
        return Observables;
    }

    public ModelGetAddress getAddress() {
        return address;
    }
    //______________________________________________________________________________________________ End getObservables
}
