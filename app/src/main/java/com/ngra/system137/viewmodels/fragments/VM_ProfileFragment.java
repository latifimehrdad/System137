package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import io.reactivex.subjects.PublishSubject;

public class VM_ProfileFragment {

    private Context context;
    private PublishSubject<String> Observable;

    public VM_ProfileFragment(Context context) {//___________________________________________________ Start VM_ProfileFragment
        this.context = context;
        Observable = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_ProfileFragment


    public void EditProfile(String UserName, String Password) {//___________________________________ Start SignUp

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SaveToken();
            }
        },2000);

    }//_____________________________________________________________________________________________ End SignUp



    private void SaveToken() {//____________________________________________________________________ Start SaveToken

        SharedPreferences.Editor token =
                context.getSharedPreferences("system137token", 0).edit();
        token.putBoolean("login", true);
        token.apply();
        Observable.onNext("EditProfileOk");

    }//_____________________________________________________________________________________________ End SaveToken



    public PublishSubject<String> getObservable() {//_______________________________________________ Start getObservable
        return Observable;
    }//_____________________________________________________________________________________________ End getObservable
}
