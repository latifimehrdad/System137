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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentHomeBinding;
import com.ngra.system137.utility.MaterialShowCaseView.MaterialShowcaseSequence;
import com.ngra.system137.utility.MaterialShowCaseView.ShowcaseConfig;
import com.ngra.system137.viewmodels.fragments.VM_HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.ngra.system137.views.activity.MainActivity.Login;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private VM_HomeFragment vm_homeFragment;
    private View view;
    private NavController navController;


    @BindView(R.id.NewRequest)
    LinearLayout NewRequest;

    @BindView(R.id.FallowRequest)
    LinearLayout FallowRequest;


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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShowCaseHelp();
            }
        },500);
        return view;
    }//_____________________________________________________________________________________________ End HomeFragment


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        CheckLogin();
        SetClick();

    }//_____________________________________________________________________________________________ End onStart


    private void ShowCaseHelp() {//_________________________________________________________________ Start ShowCaseHelp

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(100); // half second between each showcase view

        String SHOWCASE_ID = "sequence home";
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(
                NewRequest,
                getResources().getString(R.string.HelpNewRequest),
                getResources().getString(R.string.Next),"Rectangle");

        sequence.addSequenceItem(
                FallowRequest,
                getResources().getString(R.string.HelpFallowRequest),
                getResources().getString(R.string.Next),"Rectangle");

        sequence.addSequenceItem(
                TotalRequest,
                getResources().getString(R.string.HelpTotalRequest),
                getResources().getString(R.string.Next),"Rectangle");

        sequence.addSequenceItem(
                InProgress,
                getResources().getString(R.string.HelpInProgress),
                getResources().getString(R.string.Next),"Rectangle");

        sequence.addSequenceItem(
                Answered,
                getResources().getString(R.string.HelpAnswered),
                getResources().getString(R.string.Next),"Rectangle");

        sequence.addSequenceItem(
                FollowUp,
                getResources().getString(R.string.HelpFollowUp),
                getResources().getString(R.string.Close),"Rectangle");

        sequence.start();


    }//_____________________________________________________________________________________________ End ShowCaseHelp




    private void CheckLogin() {//___________________________________________________________________ Start CheckLogin
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

        FallowRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "FallowRequest");
                navController.navigate(R.id.action_homeFragment_to_fallowRequestFragment, bundle);
            }
        });

        TotalRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "TotalRequest");
                navController.navigate(R.id.action_homeFragment_to_fallowRequestFragment, bundle);
            }
        });

        InProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "InProgress");
                navController.navigate(R.id.action_homeFragment_to_fallowRequestFragment, bundle);
            }
        });

        Answered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "Answered");
                navController.navigate(R.id.action_homeFragment_to_fallowRequestFragment, bundle);
            }
        });

        FollowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "FollowUp");
                navController.navigate(R.id.action_homeFragment_to_fallowRequestFragment, bundle);
            }
        });


    }//_____________________________________________________________________________________________ End SetClick





}
