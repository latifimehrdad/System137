package com.ngra.system137.views.adabters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.AdabterItemRequestsBinding;
import com.ngra.system137.models.ModelFallowRequest;
import com.ngra.system137.views.fragments.FallowRequestFragment;

import java.util.List;

import butterknife.ButterKnife;

public class RequestAdabter  extends RecyclerView.Adapter<RequestAdabter.CustomHolder>{

    private List<ModelFallowRequest> requests;
    private LayoutInflater layoutInflater;
    private FallowRequestFragment requestFragment;

    public RequestAdabter(
            List<ModelFallowRequest> requests,
            FallowRequestFragment requestFragment) {//______________________________________________ Start RequestAdabter
        this.requests = requests;
        this.requestFragment = requestFragment;
    }//_____________________________________________________________________________________________ Start RequestAdabter


    @NonNull
    @Override
    public RequestAdabter.CustomHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {//_____________________________________________________ Start CustomHolder
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new RequestAdabter.CustomHolder(DataBindingUtil.inflate(
                layoutInflater,
                R.layout.adabter_item_requests,
                parent,
                false));
    }//_____________________________________________________________________________________________ Start CustomHolder



    @Override
    public void onBindViewHolder(RequestAdabter.CustomHolder holder, int position) {//______________ Start onBindViewHolder
        ModelFallowRequest request = requests.get(position);
        holder.bind(request,position);
    }//_____________________________________________________________________________________________ Start onBindViewHolder


    @Override
    public int getItemCount() {//___________________________________________________________________ Start getItemCount
        return requests.size();
    }//_____________________________________________________________________________________________ Start getItemCount




    public class CustomHolder extends RecyclerView.ViewHolder {//___________________________________ Start class CustomHolder

        AdabterItemRequestsBinding itemRequestsBinding;

        public CustomHolder(AdabterItemRequestsBinding binding) {//____________________________________ Start CustomHolder
            super(binding.getRoot());
            this.itemRequestsBinding = binding;
            ButterKnife.bind(this, itemRequestsBinding.getRoot());
        }//_________________________________________________________________________________________ End CustomHolder


        public void bind(ModelFallowRequest request, int position) {//_____________________________________________ Start bind
            itemRequestsBinding.setRequest(request);
            itemRequestsBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            itemRequestsBinding.executePendingBindings();
        }//_________________________________________________________________________________________ End bind

    }//_____________________________________________________________________________________________ End class CustomHolder

}
