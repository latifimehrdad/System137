package com.ngra.system137.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentAboutBinding;
import com.ngra.system137.viewmodels.fragments.VM_AboutFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private Context context;
    private VM_AboutFragment vm_aboutFragment;

    public AboutFragment() {//______________________________________________________________________ Start AboutFragment
        // Required empty public constructor
    }//_____________________________________________________________________________________________ End AboutFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_aboutFragment = new VM_AboutFragment(context);
        FragmentAboutBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_about,container,false
        );
        binding.setAbout(vm_aboutFragment);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
