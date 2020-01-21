package com.ngra.system137.viewmodels.fragments;

import android.content.Context;

import io.reactivex.subjects.PublishSubject;

public class VM_SpalshFragment {

    private Context context;
    private PublishSubject<String> Observables;

    public VM_SpalshFragment(Context context) {//___________________________________________________ Start VM_SpalshFragment
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_SpalshFragment


    public void CheckLogin() {//____________________________________________________________________ Start CheckLogin

        Observables.onNext("GoToBeforeLogin");
    }//_____________________________________________________________________________________________ End CheckLogin


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
