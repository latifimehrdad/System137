package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import io.reactivex.subjects.PublishSubject;

public class VM_SignUpFragment {

    private Context context;
    private PublishSubject<String> Observables;

    public VM_SignUpFragment(Context context) {//___________________________________________________ Start VM_SignUpFragment
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_SignUpFragment

    public void SendNumber(String UserName) {//_____________________________________________________ Start SendNumber

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Observables.onNext("SignUpOk");
            }
        },2000);

    }//_____________________________________________________________________________________________ End SendNumber


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables


}
