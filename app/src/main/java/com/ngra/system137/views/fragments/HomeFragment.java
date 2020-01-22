package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentHomeBinding;
import com.ngra.system137.viewmodels.fragments.VM_HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private VM_HomeFragment vm_homeFragment;
    private View view;

    @BindView(R.id.NewRequest)
    LinearLayout NewRequest;

    @BindView(R.id.ResultRequest)
    LinearLayout ResultRequest;


    @BindView(R.id.layout1)
    LinearLayout layout1;

    @BindView(R.id.layout2)
    LinearLayout layout2;

    @BindView(R.id.layout3)
    LinearLayout layout3;

    @BindView(R.id.layout4)
    LinearLayout layout4;

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
                ,R.layout.fragment_home,
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
        SetClick();
    }//_____________________________________________________________________________________________ End onStart


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        NewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ResultRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {

        }
    });



    }//_____________________________________________________________________________________________ End SetClick


}
