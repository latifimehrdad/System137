package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import io.reactivex.subjects.PublishSubject;

public class VM_VerifyCodeFragment {

    private Context context;
    private PublishSubject<String> Observables;
    private String MessageResponcse;

    public VM_VerifyCodeFragment(Context context) {//_______________________________________________ Start VM_VerifyCodeFragment
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_VerifyCodeFragment


    public void SendNumber(String PhoneNumber) {//__________________________________________________ Start SendNumber
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Observables.onNext("SuccessfulToken");
            }
        },2000);
    }//_____________________________________________________________________________________________ End SendNumber



    public void VerifyNumber(String PhoneNumber, String verify) {//_________________________________ Start VerifyNumber

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Observables.onNext("VerifyDone");
            }
        },2000);

    }//_____________________________________________________________________________________________ End VerifyNumber



    public void GetLoginToken(String PhoneNumber) {//_______________________________________________ Start GetLoginToken

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SaveToken();
            }
        },1000);


    }//_____________________________________________________________________________________________ End GetLoginToken



    private void SaveToken() {//____________________________________________________________________ Start SaveToken

//        SharedPreferences.Editor token =
//                context.getSharedPreferences("system137token", 0).edit();
//        token.putBoolean("login", true);
//        token.apply();
        Observables.onNext("LoginDone");

    }//_____________________________________________________________________________________________ End SaveToken



    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables

    public String getMessageResponcse() {//_________________________________________________________ Start getMessageResponcse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponcse
}
