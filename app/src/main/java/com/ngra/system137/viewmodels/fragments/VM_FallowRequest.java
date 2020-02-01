package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import io.reactivex.subjects.PublishSubject;

public class VM_FallowRequest {

    private Context context;
    private PublishSubject<String> Observables;

    public VM_FallowRequest(Context context) {//____________________________________________________ Start VM_FallowRequest
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_FallowRequest



    public void GetMyRequest() {//__________________________________________________________________ Start GetMyRequest

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Observables.onNext("GetRequest");
            }
        }, 2000);

    }//_____________________________________________________________________________________________ End GetMyRequest



    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
