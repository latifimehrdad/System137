package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ngra.system137.models.ModelSpinnerItem;

import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;

public class VM_NewRequest {

    private Context context;
    private PublishSubject<String> Observables = null;
    private ArrayList<ModelSpinnerItem> types;

    public VM_NewRequest(Context context) {//_______________________________________________________ Start VM_NewRequest
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_NewRequest


    public void GetType() {//_______________________________________________________________________ Start GetType
        types = new ArrayList<>();
        ModelSpinnerItem item = new ModelSpinnerItem(1,"نوع اول");
        types.add(item);
        Observables.onNext("GetType");
    }//_____________________________________________________________________________________________ End GetType



    public void SendRequest() {//___________________________________________________________________ Start SendRequest

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Observables.onNext("SendOk");
            }
        },5000);

    }//_____________________________________________________________________________________________ End SendRequest



    public ArrayList<ModelSpinnerItem> getTypes() {//_______________________________________________ Start getTypes
        return types;
    }//_____________________________________________________________________________________________ End getTypes


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables


    public boolean CheckUserLogin() {//_____________________________________________________________ Start CheckUserLogin

        SharedPreferences prefs = context.getSharedPreferences("system137token", 0);
        if (prefs == null) {
            return false;
        } else {
            boolean login = prefs.getBoolean("login", false);
            if (login)
                return true;
            else
                return false;
        }

    }//_____________________________________________________________________________________________ End CheckUserLogin

}
