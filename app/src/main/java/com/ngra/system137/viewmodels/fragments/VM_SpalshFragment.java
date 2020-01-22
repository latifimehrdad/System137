package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.subjects.PublishSubject;

public class VM_SpalshFragment {

    private Context context;
    private PublishSubject<String> Observables;

    public VM_SpalshFragment(Context context) {//___________________________________________________ Start VM_SpalshFragment
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_SpalshFragment


    public void CheckLogin() {//____________________________________________________________________ Start CheckLogin

        SharedPreferences prefs = context.getSharedPreferences("system137token", 0);
        if (prefs == null) {
            Observables.onNext("GoToLogin");
        } else {
//            String access_token = prefs.getString("accesstoken", null);
//            String expires = prefs.getString("expires", null);
//            if ((access_token != null) || (expires != null)) {
                boolean login = prefs.getBoolean("login", false);
                if (login)
                    Observables.onNext("GoToHome");
                else
                    Observables.onNext("GoToLogin");
//            } else
//                Observables.onNext("ConfigHandlerForLogin");
        }
    }//_____________________________________________________________________________________________ End CheckLogin


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
