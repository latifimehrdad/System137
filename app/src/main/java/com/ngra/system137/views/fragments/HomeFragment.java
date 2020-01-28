package com.ngra.system137.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentHomeBinding;
import com.ngra.system137.viewmodels.fragments.VM_HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private VM_HomeFragment vm_homeFragment;
    private View view;
    private NavController navController;
    private boolean Login;

    @BindView(R.id.NewRequest)
    LinearLayout NewRequest;

    @BindView(R.id.ResultRequest)
    LinearLayout ResultRequest;


    @BindView(R.id.TotalRequest)
    LinearLayout TotalRequest;

    @BindView(R.id.TotalRequestCount)
    TextView TotalRequestCount;

    @BindView(R.id.InProgress)
    LinearLayout InProgress;

    @BindView(R.id.InProgressCount)
    TextView InProgressCount;

    @BindView(R.id.Answered)
    LinearLayout Answered;

    @BindView(R.id.AnsweredCount)
    TextView AnsweredCount;

    @BindView(R.id.FollowUp)
    LinearLayout FollowUp;

    @BindView(R.id.FollowUpCount)
    TextView FollowUpCount;

    public HomeFragment() {//_______________________________________________________________________ Start HomeFragment

    }//_____________________________________________________________________________________________ End HomeFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start HomeFragment
        context = getContext();
        vm_homeFragment = new VM_HomeFragment(context);
        FragmentHomeBinding binding = DataBindingUtil.inflate(
                inflater
                , R.layout.fragment_home,
                container,
                false
        );
        binding.setHome(vm_homeFragment);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End HomeFragment


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        CheckLogin();
        SetClick();

    }//_____________________________________________________________________________________________ End onStart


    private void CheckLogin() {//___________________________________________________________________ Start CheckLogin
        Login = vm_homeFragment.CheckUserLogin();
        if (Login) {
            SetCountsToValues();
        } else {
            SetCountsToZero();
        }
    }//_____________________________________________________________________________________________ End CheckLogin


    private void SetCountsToValues() {//____________________________________________________________ Start SetCountsToValues

        TotalRequest.setBackgroundResource(R.drawable.home_back_circle_accent_tleft);
        InProgress.setBackgroundResource(R.drawable.home_back_circle_primery_tright);
        FollowUp.setBackgroundResource(R.drawable.home_back_circle_accent_bright);
        Answered.setBackgroundResource(R.drawable.home_back_circle_primery_bleft);

    }//_____________________________________________________________________________________________ End SetCountsToValues


    private void SetCountsToZero() {//______________________________________________________________ Start SetCountsToZero

        TotalRequestCount.setText("0");
        InProgressCount.setText("0");
        AnsweredCount.setText("0");
        FollowUpCount.setText("0");
        TotalRequest.setBackgroundResource(R.drawable.home_back_circle_gray_tleft);
        InProgress.setBackgroundResource(R.drawable.home_back_circle_gray_tright);
        FollowUp.setBackgroundResource(R.drawable.home_back_circle_gray_bright);
        Answered.setBackgroundResource(R.drawable.home_back_circle_gray_bleft);

    }//_____________________________________________________________________________________________ End SetCountsToZero


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        NewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_newRequestFragment);
            }
        });

        ResultRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TotalRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        InProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Answered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FollowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }//_____________________________________________________________________________________________ End SetClick





}
