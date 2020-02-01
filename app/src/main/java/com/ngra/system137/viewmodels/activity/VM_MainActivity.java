package com.ngra.system137.viewmodels.activity;

import android.content.Context;
import android.content.SharedPreferences;


public class VM_MainActivity {

    private Context context;

    public VM_MainActivity(Context context) {//_____________________________________________________ Start VM_MainActivity
        this.context = context;
    }//_____________________________________________________________________________________________ End VM_MainActivity



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
