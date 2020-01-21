package com.ngra.system137.viewmodels.fragments;

        import android.content.Context;
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
                Observables.onNext("LoginOk");
            }
        },2000);

    }//_____________________________________________________________________________________________ End GetLoginToken


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
