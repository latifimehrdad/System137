package com.ngra.system137.views.adabters;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.system137.R;
import com.ngra.system137.databinding.AdabterItemFilesBinding;
import com.ngra.system137.views.fragments.NewRequestFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilesAdabter extends RecyclerView.Adapter<FilesAdabter.CustomHolder> {

    private List<String> Files;
    private NewRequestFragment newRequestFragment;
    private LayoutInflater layoutInflater;

    public FilesAdabter(List<String> files, NewRequestFragment newRequestFragment) {//__________________ Start FilesAdabter
        Files = files;
        this.newRequestFragment = newRequestFragment;
    }//_____________________________________________________________________________________________ Start CustomHolder


    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//_____________ Start CustomHolder
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(
                layoutInflater,
                R.layout.adabter_item_files,
                parent,
                false));
    }//_____________________________________________________________________________________________ Start CustomHolder



    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {//____________________ Start onBindViewHolder
        String path = Files.get(position);
        String filename=path.substring(path.lastIndexOf("/")+1);
        holder.bind(filename,position);
    }//_____________________________________________________________________________________________ Start onBindViewHolder


    @Override
    public int getItemCount() {//___________________________________________________________________ Start getItemCount
        return Files.size();
    }//_____________________________________________________________________________________________ Start getItemCount


    public class CustomHolder extends RecyclerView.ViewHolder {//___________________________________ Start class CustomHolder

        @BindView(R.id.FileDelete)
        ImageView FileDelete;

        AdabterItemFilesBinding filesBinding;

        public CustomHolder(AdabterItemFilesBinding binding) {//____________________________________ Start CustomHolder
            super(binding.getRoot());
            this.filesBinding = binding;
            ButterKnife.bind(this, filesBinding.getRoot());
        }//_________________________________________________________________________________________ End CustomHolder


        public void bind(String file, int position) {//_____________________________________________ Start bind
            filesBinding.setFile(file);
            FileDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filesBinding.getRoot().setVisibility(View.INVISIBLE);
                    Animation RighttoLeft = AnimationUtils.loadAnimation(FileDelete.getContext(), R.anim.slide_delete_item);
                    filesBinding.getRoot().setAnimation(RighttoLeft);
                    filesBinding.getRoot().setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newRequestFragment.DeleteFiles(position);
                        }
                    },200);

                }
            });
            filesBinding.executePendingBindings();
        }//_________________________________________________________________________________________ End bind

    }//_____________________________________________________________________________________________ End class CustomHolder

}
