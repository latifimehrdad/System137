package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentHomeBinding;
import com.ngra.system137.viewmodels.fragments.VM_HomeFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private VM_HomeFragment vm_homeFragment;
    private View view;

    public HomeFragment() {//_______________________________________________________________________ Start HomeFragment
        context = getContext();
    }//_____________________________________________________________________________________________ End HomeFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start HomeFragment
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
    }//_____________________________________________________________________________________________ End onStart



}
