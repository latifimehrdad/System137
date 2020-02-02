package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.os.Handler;

import com.ngra.system137.models.ModelFallowRequest;
import com.ngra.system137.models.ModelSurvey;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class VM_Survey {

    private Context context;
    private PublishSubject<String> Observables;
    private List<ModelSurvey> surveys;

    public VM_Survey(Context context) {//___________________________________________________________ Start VM_Survey
        this.context = context;
        Observables = PublishSubject.create();
        surveys = new ArrayList<>();
    }//_____________________________________________________________________________________________ End VM_Survey



    public void GetSurvey() {//_____________________________________________________________________ Start GetSurvey

        surveys.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                surveys.add(new ModelSurvey("نظرسنجی1","متن سوال اول","جواب 1", "جواب 2", "جواب 3", "جواب 4"));
                Observables.onNext("GetSurvey");
            }
        }, 100);

    }//_____________________________________________________________________________________________ End GetSurvey


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables


    public List<ModelSurvey> getSurveys() {//_______________________________________________________ Start getSurveys
        return surveys;
    }//_____________________________________________________________________________________________ End getSurveys
}
