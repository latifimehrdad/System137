package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentSignupBinding;
import com.ngra.system137.utility.StaticFunctions;
import com.ngra.system137.viewmodels.fragments.VM_SignUpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ngra.system137.utility.StaticFunctions.TextChangeForChangeBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    private Context context;
    private VM_SignUpFragment vm_signUpFragment;
    private DisposableObserver<String> observer;
    private NavController navController;
    private View view;
    private String PhoneNumber;


    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.ButtonSignUp)
    RelativeLayout ButtonSignUp;

    @BindView(R.id.imgProgress)
    ImageView imgProgress;

    @BindView(R.id.BtnLoginText)
    TextView BtnLoginText;

    @BindView(R.id.ProgressGif)
    GifView ProgressGif;


    public SignUpFragment() {//_____________________________________________________________________ Start SignUpFragment

    }//_____________________________________________________________________________________________ End SignUpFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_signUpFragment = new VM_SignUpFragment(context);
        FragmentSignupBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_signup,container,false
        );
        binding.setSignup(vm_signUpFragment);
        view = binding.getRoot();
        ButterKnife.bind(this,view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        DismissLoading();
        SetClick();
        SetTextWatcher();
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservable();
    }//_____________________________________________________________________________________________ End onStart



    private void SetClick() {//_____________________________________________________________________ Start SetClick


        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticFunctions.isCancel) {
                    if (CheckEmpty()) {
                        ShowLoading();
                        vm_signUpFragment.SendNumber(
                                EditPhoneNumber.getText().toString());
                    }
                } else {
                    StaticFunctions.isCancel = true;
                    DismissLoading();
                }
            }
        });


    }//_____________________________________________________________________________________________ End SetClick



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
                                    case "SignUpOk":
                                        if(observer != null)
                                            observer.dispose();
                                        observer = null;
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PhoneNumber", PhoneNumber);
                                        navController.navigate(
                                                R.id.action_signUpFragment_to_verifyCodeFragment,
                                                bundle);
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


        vm_signUpFragment
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



    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

        boolean mobile = true;
        PhoneNumber = EditPhoneNumber.getText().toString();

        if (EditPhoneNumber.getText().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.edit_empty_background);
            EditPhoneNumber.setError(getResources().getString(R.string.EmptyMobile));
            EditPhoneNumber.requestFocus();
            mobile = false;
        } else {
            String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
            if(!ZeroNine.equalsIgnoreCase("09")) {
                EditPhoneNumber.setBackgroundResource(R.drawable.edit_empty_background);
                EditPhoneNumber.setError(getResources().getString(R.string.EmptyMobile));
                EditPhoneNumber.requestFocus();
                mobile = false;
            }
        }

        if (mobile)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ End CheckEmpty



    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        StaticFunctions.isCancel = true;
        BtnLoginText.setText(getResources().getString(R.string.GetVerifyCode));
        ButtonSignUp.setBackground(getResources().getDrawable(R.drawable.button_bg));
        ProgressGif.setVisibility(View.GONE);
        imgProgress.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        StaticFunctions.isCancel = false;
        BtnLoginText.setText(getResources().getString(R.string.Cancel));
        ButtonSignUp.setBackground(getResources().getDrawable(R.drawable.button_red));
        ProgressGif.setVisibility(View.VISIBLE);
        imgProgress.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading


}
