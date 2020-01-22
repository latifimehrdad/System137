package com.ngra.system137.viewmodels.fragments;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Handler;

        import io.reactivex.subjects.PublishSubject;

public class VM_LoginFragment {

    private Context context;
    private PublishSubject<String> Observables;

    public VM_LoginFragment(Context context) {//____________________________________________________ Start VM_LoginFragment
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_LoginFragment


    public void GetLoginToken(String UserName, String Password) {//_________________________________ Start GetLoginToken

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SaveToken();
            }
        },2000);

    }//_____________________________________________________________________________________________ End GetLoginToken


    private void SaveToken() {//____________________________________________________________________ Start SaveToken

        SharedPreferences.Editor token =
                context.getSharedPreferences("system137token", 0).edit();
        token.putBoolean("login", true);
        token.apply();
        Observables.onNext("LoginOk");

    }//_____________________________________________________________________________________________ End SaveToken


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
