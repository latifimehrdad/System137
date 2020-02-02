package com.ngra.system137.views.adabters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.AdabterItemSurveyBinding;
import com.ngra.system137.models.ModelSurvey;
import com.ngra.system137.views.fragments.SurveyFragment;

import java.util.List;

import butterknife.ButterKnife;

public class SurveyAdabter  extends RecyclerView.Adapter<SurveyAdabter.CustomHolder>{

    private List<ModelSurvey> surveys;
    private LayoutInflater layoutInflater;
    private SurveyFragment surveyFragment;

    public SurveyAdabter(
            List<ModelSurvey> surveys,
            SurveyFragment surveyFragment) {//______________________________________________ Start RequestAdabter
        this.surveys = surveys;
        this.surveyFragment = surveyFragment;
    }//_____________________________________________________________________________________________ Start RequestAdabter


    @NonNull
    @Override
    public SurveyAdabter.CustomHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {//_____________________________________________________ Start CustomHolder
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new SurveyAdabter.CustomHolder(DataBindingUtil.inflate(
                layoutInflater,
                R.layout.adabter_item_survey,
                parent,
                false));
    }//_____________________________________________________________________________________________ Start CustomHolder



    @Override
    public void onBindViewHolder(SurveyAdabter.CustomHolder holder, int position) {//______________ Start onBindViewHolder
        ModelSurvey survey = surveys.get(position);
        holder.bind(survey,position);
    }//_____________________________________________________________________________________________ Start onBindViewHolder


    @Override
    public int getItemCount() {//___________________________________________________________________ Start getItemCount
        return surveys.size();
    }//_____________________________________________________________________________________________ Start getItemCount




    public class CustomHolder extends RecyclerView.ViewHolder {//___________________________________ Start class CustomHolder

        AdabterItemSurveyBinding itemSurveyBinding;

        public CustomHolder(AdabterItemSurveyBinding binding) {//____________________________________ Start CustomHolder
            super(binding.getRoot());
            this.itemSurveyBinding = binding;
            ButterKnife.bind(this, itemSurveyBinding.getRoot());
        }//_________________________________________________________________________________________ End CustomHolder


        public void bind(ModelSurvey survey, int position) {//______________________________________ Start bind
            itemSurveyBinding.setSurvey(survey);
            itemSurveyBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    surveyFragment.ShowAnswer();
                }
            });
            itemSurveyBinding.executePendingBindings();
        }//_________________________________________________________________________________________ End bind

    }//_____________________________________________________________________________________________ End class CustomHolder

}