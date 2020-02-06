package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentSpalshBinding;
import com.ngra.system137.viewmodels.fragments.VM_SpalshFragment;
import com.ngra.system137.views.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpalshFragment extends Fragment {


    private Context context;
    private VM_SpalshFragment vm_spalshFragment;
    private View view;
    private DisposableObserver<String> observer;
    private NavController navController;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    public SpalshFragment() {//_____________________________________________________________________ Start SpalshFragment

    }//_____________________________________________________________________________________________ End SpalshFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        vm_spalshFragment = new VM_SpalshFragment(context);
        FragmentSpalshBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_spalsh,container, false
        );
        binding.setSplash(vm_spalshFragment);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        if(observer != null)
            observer.dispose();
        observer = null;
        Animation fade = AnimationUtils.loadAnimation(context, R.anim.fade_in_repeat);
        imgLoading.setAnimation(fade);

        ObserverObservable();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vm_spalshFragment.CheckLogin();
            }
        }, 5000);
    }//_____________________________________________________________________________________________ End onStart



    private void ObserverObservable() {//___________________________________________________________ Start ObserverObservable

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                getActivity()
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(observer != null)
                                    observer.dispose();
                                observer = null;
                                switch (s) {
                                    case "GoToLogin":
                                        navController.navigate(R.id.action_spalshFragment_to_LoginFragment);
                                        break;
                                    case "GoToHome":
                                        navController.navigate(
                                                R.id.action_spalshFragment_to_homeFragment);
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


        vm_spalshFragment
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservable



    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        observer.dispose();
    }//_____________________________________________________________________________________________ End onDestroy

}
