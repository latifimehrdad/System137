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


    public void GetMyRequest(String Type) {//_______________________________________________________ Start GetMyRequest

        requests.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (Type) {
                    case "FallowRequest":
                        GetFallowRequest();
                        break;
                    case "TotalRequest":
                        GetTotalRequest();
                        break;
                    case "InProgress":
                        GetInProgress();
                        break;
                    case "Answered":
                        GetAnswered();
                        break;
                    case "FollowUp":
                        GetFollowUpt();
                        break;
                }
            }
        }, 100);

    }//_____________________________________________________________________________________________ End GetMyRequest


    private void GetFallowRequest() {//_____________________________________________________________ Start getObservables

        requests.add(new ModelFallowRequest(101, 1, "موضوع اول", "موضوع اول شامل توضیحاتی پیرامون شرکت نگرا و عملکرد ان در سال جاری می باشد", 1));
        requests.add(new ModelFallowRequest(102, 2, "موضوع دوم", "به گزارش خبرگزاری مهر به نقل از ایندپندنت، آپدیت جدید واتس اپ سبب می شود از امروز(یکم فوریه ۲۰۲۰) دسترسی میلون ها کاربری که از آیفون ها و دستگاه های قدیمی اندروید استفاده می کنند، به این اپلیکیشن قطع شود", 2));

        Observables.onNext("GetRequest");

    }//_____________________________________________________________________________________________ End getObservables


    private void GetTotalRequest() {//______________________________________________________________ Start GetTotalRequest

        requests.add(new ModelFallowRequest(101, 1, "موضوع اول", "موضوع اول شامل توضیحاتی پیرامون شرکت نگرا و عملکرد ان در سال جاری می باشد", 1));
        requests.add(new ModelFallowRequest(102, 2, "موضوع دوم", "به گزارش خبرگزاری مهر به نقل از ایندپندنت، آپدیت جدید واتس اپ سبب می شود از امروز(یکم فوریه ۲۰۲۰) دسترسی میلون ها کاربری که از آیفون ها و دستگاه های قدیمی اندروید استفاده می کنند، به این اپلیکیشن قطع شود", 2));
        requests.add(new ModelFallowRequest(103, 3, "موضوع سوم", "هدف از این پروژه نشان دادن بهترین شیوه ها ، ارائه مجموعه ای از دستورالعمل ها و ارائه معماری مدرن برنامه اندرویدی است که مدولار ، مقیاس پذیر ، قابل اطمینان و قابل آزمایش است.", 3));
        Observables.onNext("GetRequest");

    }//_____________________________________________________________________________________________ End GetTotalRequest


    private void GetInProgress() {//________________________________________________________________ Start GetInProgress

        requests.add(new ModelFallowRequest(101, 1, "موضوع اول", "موضوع اول شامل توضیحاتی پیرامون شرکت نگرا و عملکرد ان در سال جاری می باشد", 1));
        Observables.onNext("GetRequest");

    }//_____________________________________________________________________________________________ End GetInProgress


    private void GetAnswered() {//__________________________________________________________________ Start GetAnswered

        requests.add(new ModelFallowRequest(102, 2, "موضوع دوم", "به گزارش خبرگزاری مهر به نقل از ایندپندنت، آپدیت جدید واتس اپ سبب می شود از امروز(یکم فوریه ۲۰۲۰) دسترسی میلون ها کاربری که از آیفون ها و دستگاه های قدیمی اندروید استفاده می کنند، به این اپلیکیشن قطع شود", 2));
        Observables.onNext("GetRequest");

    }//_____________________________________________________________________________________________ End GetAnswered


    private void GetFollowUpt() {//_________________________________________________________________ Start GetFollowUpt

        requests.add(new ModelFallowRequest(103, 3, "موضوع سوم", "هدف از این پروژه نشان دادن بهترین شیوه ها ، ارائه مجموعه ای از دستورالعمل ها و ارائه معماری مدرن برنامه اندرویدی است که مدولار ، مقیاس پذیر ، قابل اطمینان و قابل آزمایش است.", 3));
        Observables.onNext("GetRequest");

    }//_____________________________________________________________________________________________ End GetFollowUpt


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables


    public List<ModelFallowRequest> getRequests() {//_______________________________________________ Start getRequests
        return requests;
    }//_____________________________________________________________________________________________ End getRequests
}
