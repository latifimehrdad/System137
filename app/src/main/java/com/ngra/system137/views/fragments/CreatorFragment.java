package com.ngra.system137.views.fragments;


        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;

        import androidx.databinding.DataBindingUtil;
        import androidx.fragment.app.Fragment;

        import android.os.Handler;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.codesgood.views.JustifiedTextView;
        import com.ngra.system137.R;
        import com.ngra.system137.databinding.FragmentCreatorBinding;
        import com.ngra.system137.viewmodels.fragments.VM_CreatorFragment;
        import com.ngra.system137.views.activity.MainActivity;

        import butterknife.BindView;
        import butterknife.ButterKnife;

        import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatorFragment extends Fragment {

    private Context context;
    private VM_CreatorFragment vm_creatorFragment;

    @BindView(R.id.NgraSite)
    TextView NgraSite;

    @BindView(R.id.imgNgraLogo)
    ImageView imgNgraLogo;

    @BindView(R.id.tv_justified_paragraph)
    JustifiedTextView tv_justified_paragraph;


    public CreatorFragment() {//____________________________________________________________________ Start CreatorFragment
        // Required empty public constructor
    }//_____________________________________________________________________________________________ End CreatorFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_creatorFragment = new VM_CreatorFragment(context);
        FragmentCreatorBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_creator,container,false
        );
        binding.setCreator(vm_creatorFragment);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetClicks();
        imgNgraLogo.setVisibility(View.INVISIBLE);
        NgraSite.setVisibility(View.INVISIBLE);
        tv_justified_paragraph.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SetAnimation();
            }
        },500);

    }//_____________________________________________________________________________________________ End onStart



    private void SetAnimation() {//_________________________________________________________________ Start SetAnimation

        Animation slide_in_left1 = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        imgNgraLogo.setAnimation(slide_in_left1);
        imgNgraLogo.setVisibility(View.VISIBLE);

        Animation slide_in_left2 = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        NgraSite.setAnimation(slide_in_left2);
        NgraSite.setVisibility(View.VISIBLE);

        Animation slide_in_right = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        tv_justified_paragraph.setAnimation(slide_in_right);
        tv_justified_paragraph.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End SetAnimation



    private void SetClicks() {//____________________________________________________________________ Start SetClicks
        NgraSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.ngra.ir"));
                startActivity(intent);
            }
        });
    }//_____________________________________________________________________________________________ End SetClicks

}
