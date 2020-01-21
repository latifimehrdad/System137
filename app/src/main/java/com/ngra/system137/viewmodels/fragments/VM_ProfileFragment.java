package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import io.reactivex.subjects.PublishSubject;

public class VM_ProfileFragment {

    private Context context;
    private PublishSubject<String> Observable;

    public VM_ProfileFragment(Context context) {//___________________________________________________ Start VM_ProfileFragment
        this.context = context;
        Observable = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_ProfileFragment


    public void SignUp(String UserName, String Password) {//________________________________________ Start SignUp

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Observable.onNext("SignUpOk");
            }
        },2000);

    }//_____________________________________________________________________________________________ End SignUp




    public PublishSubject<String> getObservable() {//_______________________________________________ Start getObservable
        return Observable;
    }//_____________________________________________________________________________________________ End getObservable
}
