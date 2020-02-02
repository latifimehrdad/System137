package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentSurveyBinding;
import com.ngra.system137.viewmodels.fragments.VM_Survey;
import com.ngra.system137.views.adabters.RequestAdabter;
import com.ngra.system137.views.adabters.SurveyAdabter;
import com.ngra.system137.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private Context context;
    private View view;
    private DisposableObserver<String> observer;
    private VM_Survey vm_survey;
    private SurveyAdabter surveyAdabter;


    @BindView(R.id.RecyclerSurvey)
    RecyclerView RecyclerSurvey;

    @BindView(R.id.layoutAnswers)
    LinearLayout layoutAnswers;

    @BindView(R.id.ButtonSend)
    RelativeLayout ButtonSend;

    public SurveyFragment() {//______________________________________________________________ Start FallowRequestFragment
        // Required empty public constructor
    }//_____________________________________________________________________________________________ End FallowRequestFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_survey = new VM_Survey(context);
        FragmentSurveyBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_survey,container,false
        );
        binding.setSurvey(vm_survey);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservable();
        GetSurvey();
        layoutAnswers.setVisibility(View.GONE);
        SetClick();
    }//_____________________________________________________________________________________________ End onStart



    private void SetClick() {//_____________________________________________________________________ Start SetClick

        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutAnswers.setVisibility(View.GONE);
            }
        });
    }//_____________________________________________________________________________________________ End SetClick


    private void GetSurvey() {//____________________________________________________________________ Start GetSurvey
        vm_survey.GetSurvey();
    }//_____________________________________________________________________________________________ End GetSurvey


    private void ObserverObservable() {//___________________________________________________________ Start ObserverObservable

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                getActivity()
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (s) {
                                    case "GetSurvey":
                                        if(observer != null)
                                            observer.dispose();
                                        observer = null;
                                        SetAdabterSurvey();
                                        break;
                                }
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        vm_survey
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservable



    private void SetAdabterSurvey() {//_____________________________________________________________ Start SetAdabterSurvey

        surveyAdabter = new SurveyAdabter(vm_survey.getSurveys(), SurveyFragment.this);
        RecyclerSurvey.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        RecyclerSurvey.setAdapter(surveyAdabter);

    }//_____________________________________________________________________________________________ End SetAdabterSurvey



    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ End onDestroy



    public void ShowAnswer() {//____________________________________________________________________ Start ShowAnswer
        layoutAnswers.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End ShowAnswer


}
