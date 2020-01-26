package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;

public class VM_HomeFragment {

    private Context context;

    public VM_HomeFragment(Context context) {//_____________________________________________________ Start VM_HomeFragment
        this.context = context;
    }//_____________________________________________________________________________________________ Start VM_HomeFragment


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
