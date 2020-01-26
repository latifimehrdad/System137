package com.ngra.system137.dagger.retrofit;


import com.ngra.system137.dagger.DaggerScope;

import dagger.Component;

@DaggerScope
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    RetrofitApiInterface getRetrofitApiInterface();
}
