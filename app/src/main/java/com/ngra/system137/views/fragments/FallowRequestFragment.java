package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentFallowRequstBinding;
import com.ngra.system137.utility.StaticFunctions;
import com.ngra.system137.viewmodels.fragments.VM_FallowRequest;
import com.ngra.system137.views.adabters.FilesAdabter;
import com.ngra.system137.views.adabters.RequestAdabter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ngra.system137.utility.StaticFunctions.TextChangeForChangeBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class FallowRequestFragment extends Fragment {

    private Context context;
    private View view;
    private VM_FallowRequest vm_fallowRequest;
    private DisposableObserver<String> observer;
    private RequestAdabter requestAdabter;

    @BindView(R.id.RequestCode)
    EditText RequestCode;

    @BindView(R.id.BtnSearch)
    RelativeLayout BtnSearch;

    @BindView(R.id.imgProgress)
    ImageView imgProgress;

    @BindView(R.id.BtnSearchText)
    TextView BtnSearchText;

    @BindView(R.id.ProgressGif)
    GifView ProgressGif;

    @BindView(R.id.RecyclerRequest)
    RecyclerView RecyclerRequest;



    public FallowRequestFragment() {//______________________________________________________________ Start FallowRequestFragment

    }//_____________________________________________________________________________________________ End FallowRequestFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_fallowRequest = new VM_FallowRequest(context);
        FragmentFallowRequstBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_fallow_requst,container,false
        );
        binding.setFallowRequest(vm_fallowRequest);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetClick();
        SetTextWatcher();
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservable();
        GetMyRequest();
    }//_____________________________________________________________________________________________ End onStart



    private void SetClick() {//_____________________________________________________________________ Start SetClick

        BtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RequestCode.getText().length() == 0) {
                    RequestCode.setBackgroundResource(R.drawable.edit_empty_background);
                    RequestCode.setError(getResources().getString(R.string.EmptyRequestCode));
                    RequestCode.requestFocus();
                    return;
                }
                GetMyRequest();
            }
        });

    }//_____________________________________________________________________________________________ End SetClick



    private void GetMyRequest() {//_________________________________________________________________ Start GetMyRequest
        ShowLoading();
        vm_fallowRequest.GetMyRequest();
    }//_____________________________________________________________________________________________ End GetMyRequest


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        RequestCode.addTextChangedListener(TextChangeForChangeBack(RequestCode));
    }//_____________________________________________________________________________________________ End SetTextWatcher



    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        StaticFunctions.isCancel = true;
        BtnSearchText.setText(getResources().getString(R.string.Search));
        BtnSearch.setBackground(getResources().getDrawable(R.drawable.button_bg));
        ProgressGif.setVisibility(View.GONE);
        imgProgress.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        StaticFunctions.isCancel = false;
        BtnSearchText.setText(getResources().getString(R.string.Cancel));
        BtnSearch.setBackground(getResources().getDrawable(R.drawable.button_red));
        ProgressGif.setVisibility(View.VISIBLE);
        imgProgress.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading




    private void ObserverObservable() {//___________________________________________________________ Start ObserverObservable

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                getActivity()
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DismissLoading();
                                switch (s) {
                                    case "GetRequest":
                                        if(observer != null)
                                            observer.dispose();
                                        observer = null;
                                        SetAdabterRequest();
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


        vm_fallowRequest
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservable



    private void SetAdabterRequest() {//____________________________________________________________ Start SetAdabterRequest

        requestAdabter = new RequestAdabter(vm_fallowRequest.getRequests(), FallowRequestFragment.this);
        RecyclerRequest.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        RecyclerRequest.setAdapter(requestAdabter);

    }//_____________________________________________________________________________________________ End SetAdabterRequest



    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ End onDestroy





}
