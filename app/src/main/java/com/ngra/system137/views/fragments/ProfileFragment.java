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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.system137.R;
import com.ngra.system137.daggers.RetrofitModule;
import com.ngra.system137.databinding.FragmentProfileBinding;
import com.ngra.system137.viewmodels.fragments.VM_ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ngra.system137.utility.StaticFunctions.TextChangeForChangeBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private Context context;
    private VM_ProfileFragment vm_profileFragment;
    private View view;
    private DisposableObserver<String> observer;
    private int GenderCode;
    private NavController navController;


    @BindView(R.id.EditUser)
    EditText EditUser;

    @BindView(R.id.EditPassword)
    EditText EditPassword;

    @BindView(R.id.BtnLogin)
    RelativeLayout BtnLogin;

    @BindView(R.id.imgProgress)
    ImageView imgProgress;

    @BindView(R.id.BtnLoginText)
    TextView BtnLoginText;

    @BindView(R.id.ProgressGif)
    GifView ProgressGif;

    @BindView(R.id.EditName)
    EditText EditName;

    @BindView(R.id.EditFamily)
    EditText EditFamily;

    @BindView(R.id.EditNational)
    EditText EditNational;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.radioMan)
    RadioButton radioMan;

    @BindView(R.id.radioWoman)
    RadioButton radioWoman;

    @BindView(R.id.layoutGender)
    RadioGroup layoutGender;


    public ProfileFragment() {//_____________________________________________________________________ Start ProfileFragment
        context = getContext();
    }//_____________________________________________________________________________________________ End ProfileFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        vm_profileFragment = new VM_ProfileFragment(context);
        FragmentProfileBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false
        );
        binding.setSignup(vm_profileFragment);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        DismissLoading();
        SetClick();
        SetTextWatcher();
        if (observer != null)
            observer.dispose();
        observer = null;
        ObserverObservable();
    }//_____________________________________________________________________________________________ End onStart


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RetrofitModule.isCancel) {
                    if (CheckEmpty()) {
                        ShowLoading();
                        vm_profileFragment.SignUp(
                                EditUser.getText().toString(),
                                EditPassword.getText().toString());
                    }
                } else {
                    RetrofitModule.isCancel = true;
                    DismissLoading();
                }
            }
        });



        radioMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioMan.isChecked()) {
                    layoutGender.setBackgroundColor(getResources().getColor(R.color.ML_White));
                    GenderCode = 1;
                }
            }
        });


        radioWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioMan.isChecked()) {
                    layoutGender.setBackgroundColor(getResources().getColor(R.color.ML_White));
                    GenderCode = 0;
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private void ObserverObservable() {//___________________________________________________________ Start ObserverObservable

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                getActivity()
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DismissLoading();
                                switch (s) {
                                    case "SignUpOk":
                                        if (observer != null)
                                            observer.dispose();
                                        observer = null;
                                        navController.navigate(R.id.action_profileFragment_to_homeFragment);
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


        vm_profileFragment
                .getObservable()
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

        boolean user = true;
        boolean password = true;
        boolean gender = true;
        boolean mobile = true;
        boolean national = true;
        boolean family = true;
        boolean name = true;

        if (!radioWoman.isChecked() && !radioMan.isChecked()) {
            layoutGender.setBackgroundResource(R.drawable.edit_empty_background);
            gender = false;
        }

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


        if (EditNational.getText().length() != 10) {
            EditNational.setBackgroundResource(R.drawable.edit_empty_background);
            EditNational.setError(getResources().getString(R.string.EmptyNational));
            EditNational.requestFocus();
            national = false;
        }


        if (EditFamily.getText().length() == 0) {
            EditFamily.setBackgroundResource(R.drawable.edit_empty_background);
            EditFamily.setError(getResources().getString(R.string.EmptyFamily));
            EditFamily.requestFocus();
            family = false;
        }


        if (EditName.getText().length() == 0) {
            EditName.setBackgroundResource(R.drawable.edit_empty_background);
            EditName.setError(getResources().getString(R.string.EmptyName));
            EditName.requestFocus();
            name = false;
        }


        if (EditPassword.getText().length() == 0) {
            EditPassword.setBackgroundResource(R.drawable.edit_empty_background);
            EditPassword.setError(getResources().getString(R.string.EmptyPassword));
            EditPassword.requestFocus();
            password = false;
        }

        if (EditUser.getText().length() == 0) {
            EditUser.setBackgroundResource(R.drawable.edit_empty_background);
            EditUser.setError(getResources().getString(R.string.EmptyUserName));
            EditUser.requestFocus();
            user = false;
        }


        if (user && password && gender && mobile && national && family && name)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ End CheckEmpty


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditUser.addTextChangedListener(TextChangeForChangeBack(EditUser));
        EditPassword.addTextChangedListener(TextChangeForChangeBack(EditPassword));
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
        EditNational.addTextChangedListener(TextChangeForChangeBack(EditNational));
        EditFamily.addTextChangedListener(TextChangeForChangeBack(EditFamily));
        EditName.addTextChangedListener(TextChangeForChangeBack(EditName));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        RetrofitModule.isCancel = true;
        BtnLoginText.setText(getResources().getString(R.string.Login));
        BtnLogin.setBackground(getResources().getDrawable(R.drawable.button_bg));
        ProgressGif.setVisibility(View.GONE);
        imgProgress.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        RetrofitModule.isCancel = false;
        BtnLoginText.setText(getResources().getString(R.string.Cancel));
        BtnLogin.setBackground(getResources().getDrawable(R.drawable.button_red));
        ProgressGif.setVisibility(View.VISIBLE);
        imgProgress.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading


}
