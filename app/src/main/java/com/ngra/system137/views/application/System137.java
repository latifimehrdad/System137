package com.ngra.system137.views.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.ngra.system137.R;
import com.ngra.system137.dagger.retrofit.DaggerRetrofitComponent;
import com.ngra.system137.dagger.retrofit.RetrofitComponent;
import com.ngra.system137.dagger.retrofit.RetrofitModule;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class System137 extends MultiDexApplication {

    private Context context;
    private RetrofitComponent retrofitComponent;


    @Override
    public void onCreate() {//______________________________________________________________________ Start onCreate
        super.onCreate();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
        ConfigrationRetrofitComponent();

    }//_____________________________________________________________________________________________ End onCreate

    private void ConfigrationRetrofitComponent() {
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(context))
                .build();
    }


    private void ConfigurationCalligraphy() {//_____________________________________________________ Start ConfigurationCalligraphy
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }//_____________________________________________________________________________________________ End ConfigurationCalligraphy


    public static System137 getApplication(Context context) {//_____________________________________ Start getApplication
        return (System137) context.getApplicationContext();
    }//_____________________________________________________________________________________________ End getApplication


    public RetrofitComponent getRetrofitComponent() {
        return retrofitComponent;
    }
}
