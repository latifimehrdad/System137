package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import com.ngra.system137.models.ModelFallowRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class VM_FallowRequest {

    private Context context;
    private PublishSubject<String> Observables;
    private List<ModelFallowRequest> requests;

    public VM_FallowRequest(Context context) {//____________________________________________________ Start VM_FallowRequest
        this.context = context;
        Observables = PublishSubject.create();
        requests = new ArrayList<>();
    }//_____________________________________________________________________________________________ End VM_FallowRequest



    public void GetMyRequest() {//__________________________________________________________________ Start GetMyRequest

        requests.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                requests.add(new ModelFallowRequest(101,1,"موضوع اول","موضوع اول شامل توضیحاتی پیرامون شرکت نگرا و عملکرد ان در سال جاری می باشد",1));
                requests.add(new ModelFallowRequest(102,2,"موضوع دوم","به گزارش خبرگزاری مهر به نقل از ایندپندنت، آپدیت جدید واتس اپ سبب می شود از امروز(یکم فوریه ۲۰۲۰) دسترسی میلون ها کاربری که از آیفون ها و دستگاه های قدیمی اندروید استفاده می کنند، به این اپلیکیشن قطع شود.\n" +
                        "\n" +
                        "واتس اپ اعلام کرده آیفون هایی که دارای سیستم عامل iOS۸ یا قدیمی تر هستند( دستگاه های آیفون ۴ یا قدیمی تر) تحت تاثیر آپدیت جدید قرار می گیرند. اما دستگاه های آیفون۴اس، تمام مدل های آیفون ۵ و تمام مدل های آیفون۶ همچنان از پشتیبانی واتس اپ بهره می برند.\n" +
                        "\n" +
                        "علاوه بر آن موبایل هایی که سیستم عامل آنها اندروید۲.۳.۷(اندروید جینجربرد) و قدیمی تر است نیز تحت تاثیر آپدیت جدید غیر فعال می شوند.\n" +
                        "\n" +
                        "این در حالی است که در پایان ۲۰۱۹ میلادی نیز دسترسی عده زیادی از کاربران دستگاه های مختلف از جمله موبایل های ویندوز فون به این پیامرسان قطع شد.",2));

                Observables.onNext("GetRequest");
            }
        }, 100);

    }//_____________________________________________________________________________________________ End GetMyRequest



    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables


    public List<ModelFallowRequest> getRequests() {//_______________________________________________ Start getRequests
        return requests;
    }//_____________________________________________________________________________________________ End getRequests
}
