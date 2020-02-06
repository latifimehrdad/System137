package com.ngra.system137.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ngra.system137.R;
import com.ngra.system137.databinding.ActivityMainBinding;
import com.ngra.system137.utility.MaterialShowCaseView.MaterialShowcaseSequence;
import com.ngra.system137.utility.MaterialShowCaseView.ShowcaseConfig;
import com.ngra.system137.viewmodels.activity.VM_MainActivity;
import com.ngra.system137.views.fragments.NewRequestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

    private VM_MainActivity vm_mainActivity;
    private NavController navController;
    private boolean doubleBackToExitPressedOnce = false;
    private int count = 1;
    public static boolean Login;

    @BindView(R.id.mainMenu)
    ImageView mainMenu;

    @BindView(R.id.layoutMenu)
    RelativeLayout layoutMenu;

    @BindView(R.id.layoutMenuBack)
    LinearLayout layoutMenuBack;

    @BindView(R.id.layoutMenuLogin)
    RelativeLayout layoutMenuLogin;

    @BindView(R.id.layoutMenuSiguUp)
    RelativeLayout layoutMenuSiguUp;

    @BindView(R.id.layoutMenuFallow)
    RelativeLayout layoutMenuFallow;


    @BindView(R.id.layoutMenuSurvey)
    RelativeLayout layoutMenuSurvey;


    @BindView(R.id.layoutMenuAbout)
    RelativeLayout layoutMenuAbout;

    @BindView(R.id.layoutMenuHelp)
    RelativeLayout layoutMenuHelp;

    @BindView(R.id.layoutMenuExit)
    RelativeLayout layoutMenuExit;

    @BindView(R.id.layoutNGRA)
    RelativeLayout layoutNGRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        vm_mainActivity = new VM_MainActivity(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(vm_mainActivity);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        layoutMenu.setVisibility(View.GONE);
        SetClick();
        CheckLogin();
    }//_____________________________________________________________________________________________ End onCreate


    private void CheckLogin() {//___________________________________________________________________ Start CheckLogin
        Login = vm_mainActivity.CheckUserLogin();
    }//_____________________________________________________________________________________________ End CheckLogin





    private void ShowCaseHelpLogin() {//____________________________________________________________ Start ShowCaseHelpLogin

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(50); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, true);

        LinearLayout layoutGuest = (LinearLayout) findViewById(R.id.layoutGuest);
        RelativeLayout BtnLogin = (RelativeLayout) findViewById(R.id.BtnLogin);
        Button ButtonSignUp = (Button) findViewById(R.id.ButtonSignUp);

        sequence.setConfig(config);

        sequence.addSequenceItem(
                layoutGuest,
                getResources().getString(R.string.HelpGuest),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                BtnLogin,
                getResources().getString(R.string.HelpLogin),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                ButtonSignUp,
                getResources().getString(R.string.HelpSignUp),
                getResources().getString(R.string.Close), "Rectangle");

        sequence.start();

    }//_____________________________________________________________________________________________ End ShowCaseHelpLogin


    private void ShowCaseHelpHome() {//_____________________________________________________________ Start ShowCaseHelpHome

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(100); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, true);

        LinearLayout NewRequest = (LinearLayout) findViewById(R.id.NewRequest);
        LinearLayout FallowRequest = (LinearLayout) findViewById(R.id.FallowRequest);
        LinearLayout TotalRequest = (LinearLayout) findViewById(R.id.TotalRequest);
        LinearLayout InProgress = (LinearLayout) findViewById(R.id.InProgress);
        LinearLayout Answered = (LinearLayout) findViewById(R.id.Answered);
        LinearLayout FollowUp = (LinearLayout) findViewById(R.id.FollowUp);


        sequence.setConfig(config);

        sequence.addSequenceItem(
                NewRequest,
                getResources().getString(R.string.HelpNewRequest),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                FallowRequest,
                getResources().getString(R.string.HelpFallowRequest),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                TotalRequest,
                getResources().getString(R.string.HelpTotalRequest),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                InProgress,
                getResources().getString(R.string.HelpInProgress),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                Answered,
                getResources().getString(R.string.HelpAnswered),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                FollowUp,
                getResources().getString(R.string.HelpFollowUp),
                getResources().getString(R.string.Close), "Rectangle");

        sequence.start();


    }//_____________________________________________________________________________________________ End ShowCaseHelpHome


    private void ShowCaseHelpNewRequest() {//_______________________________________________________ Start ShowCaseHelpNewRequest

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(100); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, true);

        LinearLayout layoutChooseAddress = (LinearLayout) findViewById(R.id.layoutChooseAddress);
        LinearLayout layoutAttach = (LinearLayout) findViewById(R.id.layoutAttach);
        LinearLayout layoutShowFiles = (LinearLayout) findViewById(R.id.layoutShowFiles);
        RelativeLayout ButtonSend = (RelativeLayout) findViewById(R.id.ButtonSend);

        sequence.setConfig(config);

        sequence.addSequenceItem(
                layoutChooseAddress,
                getResources().getString(R.string.HelpChooseAddress),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                layoutAttach,
                getResources().getString(R.string.HelpAttachFiles),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                layoutShowFiles,
                getResources().getString(R.string.HelpShowAttachFiles),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                ButtonSend,
                getResources().getString(R.string.HelpSendRequest),
                getResources().getString(R.string.Close), "Rectangle");

        sequence.start();


    }//_____________________________________________________________________________________________ End ShowCaseHelpNewRequest


    private void ShowCaseHelpFiles() {//____________________________________________________________ Start ShowCaseHelpFiles

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(100); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, true);

        LinearLayout layoutChooseDoc = (LinearLayout) findViewById(R.id.layoutChooseDoc);
        LinearLayout layoutChooseImage = (LinearLayout) findViewById(R.id.layoutChooseImage);
        LinearLayout layoutChooseVideo = (LinearLayout) findViewById(R.id.layoutChooseVideo);
        LinearLayout layoutRecordVoice = (LinearLayout) findViewById(R.id.layoutRecordVoice);


        sequence.setConfig(config);

        sequence.addSequenceItem(
                layoutChooseDoc,
                getResources().getString(R.string.HelpChooseDoc),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                layoutChooseImage,
                getResources().getString(R.string.HelpChooseImage),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                layoutChooseVideo,
                getResources().getString(R.string.HelpProgressVideo),
                getResources().getString(R.string.Next), "Rectangle");

        sequence.addSequenceItem(
                layoutRecordVoice,
                getResources().getString(R.string.HelpRecordVoice),
                getResources().getString(R.string.Close), "Rectangle");

        sequence.start();


    }//_____________________________________________________________________________________________ End ShowCaseHelpFiles


    private void SetClick() {//_____________________________________________________________________ Start SetClick


        layoutMenuHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDestination navDestination = navController.getCurrentDestination();
                String fragment = navDestination.getLabel().toString();
                switch (fragment) {
                    case "fragment_before_login":
                        ShowCaseHelpLogin();
                        CloseMenu();
                        break;
                    case "fragment_home":
                        ShowCaseHelpHome();
                        CloseMenu();
                        break;
                    case "fragment_new_request":
                        if (!NewRequestFragment.ShowLayoutAttachfile)
                            ShowCaseHelpNewRequest();
                        else
                            ShowCaseHelpFiles();
                        CloseMenu();
                        break;

                }


            }
        });

        layoutMenuExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });

        layoutMenuAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDestination navDestination = navController.getCurrentDestination();
                String fragment = navDestination.getLabel().toString();
                if (fragment.equalsIgnoreCase("fragment_about")) {
                    CloseMenu();
                    return;
                }

                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                navController.navigate(R.id.aboutFragment);
                CloseMenu();
            }
        });

        layoutMenuLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDestination navDestination = navController.getCurrentDestination();
                String fragment = navDestination.getLabel().toString();
                if (fragment.equalsIgnoreCase("fragment_before_login")) {
                    CloseMenu();
                    return;
                }

                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                navController.navigate(R.id.LoginFragment);
                CloseMenu();
            }
        });

        layoutMenuSiguUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDestination navDestination = navController.getCurrentDestination();
                String fragment = navDestination.getLabel().toString();
                if (fragment.equalsIgnoreCase("fragment_signup")) {
                    CloseMenu();
                    return;
                }

                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                navController.navigate(R.id.signUpFragment);
                CloseMenu();
            }
        });

        layoutMenuFallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDestination navDestination = navController.getCurrentDestination();
                String fragment = navDestination.getLabel().toString();
                if (fragment.equalsIgnoreCase("fragment_fallow_requst")) {
                    CloseMenu();
                    return;
                }

                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                navController.navigate(R.id.fallowRequestFragment);
                CloseMenu();
            }
        });

        layoutMenuSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavDestination navDestination = navController.getCurrentDestination();
                String fragment = navDestination.getLabel().toString();
                if (fragment.equalsIgnoreCase("fragment_survey")) {
                    CloseMenu();
                    return;
                }
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                navController.navigate(R.id.surveyFragment);
                CloseMenu();
            }
        });


        layoutNGRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.ngra.ir"));
                startActivity(intent);
                CloseMenu();
            }
        });

        layoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseMenu();
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layoutMenu.getVisibility() == View.VISIBLE)
                    CloseMenu();
                else
                    ShowMenu();
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private void ShowMenu() {//_____________________________________________________________________ Start ShowMenu
        NavDestination navDestination = navController.getCurrentDestination();
        String fragment = navDestination.getLabel().toString();
        if (fragment.equalsIgnoreCase("fragment_spalsh")) {
            return;
        }

        if (NewRequestFragment.InProcess) {
            Toast.makeText(this, getResources().getString(R.string.InProcess), Toast.LENGTH_SHORT).show();
            return;
        }

        if (Login) {
            count = 3;
            layoutMenuLogin.setVisibility(View.GONE);
            layoutMenuSiguUp.setVisibility(View.GONE);
        } else {
            count = 1;
            layoutMenuFallow.setVisibility(View.GONE);
            layoutMenuSurvey.setVisibility(View.GONE);
        }
        Animation slide_in_top = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);
        layoutMenuBack.setAnimation(slide_in_top);
        layoutMenuBack.setVisibility(View.VISIBLE);
        layoutMenu.setVisibility(View.VISIBLE);
        Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        mainMenu.setAnimation(bounce);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (count) {
                    case 1:
                        Animation slide_in_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuLogin.setAnimation(slide_in_right);
                        layoutMenuLogin.setVisibility(View.VISIBLE);
                        count++;
                        handler.postDelayed(this, 140);
                        break;
                    case 2:
                        Animation slide_in_right2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuSiguUp.setAnimation(slide_in_right2);
                        layoutMenuSiguUp.setVisibility(View.VISIBLE);
                        if (!Login)
                            count = 5;
                        else
                            count++;
                        handler.postDelayed(this, 150);
                        break;
                    case 3:
                        Animation slide_in_right3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuFallow.setAnimation(slide_in_right3);
                        layoutMenuFallow.setVisibility(View.VISIBLE);
                        count++;
                        handler.postDelayed(this, 160);
                        break;
                    case 4:
                        Animation slide_in_right4 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuSurvey.setAnimation(slide_in_right4);
                        layoutMenuSurvey.setVisibility(View.VISIBLE);
                        count++;
                        handler.postDelayed(this, 170);
                        break;
                    case 5:
                        Animation slide_in_right5 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuAbout.setAnimation(slide_in_right5);
                        layoutMenuAbout.setVisibility(View.VISIBLE);
                        count++;
                        handler.postDelayed(this, 180);
                        break;
                    case 6:
                        Animation slide_in_right6 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuHelp.setAnimation(slide_in_right6);
                        layoutMenuHelp.setVisibility(View.VISIBLE);
                        count++;
                        handler.postDelayed(this, 190);
                        break;
                    case 7:
                        Animation slide_in_right7 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutNGRA.setAnimation(slide_in_right7);
                        layoutNGRA.setVisibility(View.VISIBLE);
                        count++;
                        handler.postDelayed(this, 200);
                        break;
                    case 8:
                        Animation slide_in_right8 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                        layoutMenuExit.setAnimation(slide_in_right8);
                        layoutMenuExit.setVisibility(View.VISIBLE);
                        break;
                }


            }
        }, 250);


    }//_____________________________________________________________________________________________ End ShowMenu


    private void CloseMenu() {//____________________________________________________________________ Start CloseMenu

        Animation slide_out_top = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_right);
        layoutMenuBack.setAnimation(slide_out_top);
        layoutMenuBack.setVisibility(View.INVISIBLE);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainMenu.setAnimation(null);
                layoutMenu.setVisibility(View.GONE);
                layoutMenuLogin.setVisibility(View.GONE);
                layoutMenuSiguUp.setVisibility(View.GONE);
                layoutMenuFallow.setVisibility(View.GONE);
                layoutMenuSurvey.setVisibility(View.GONE);
                layoutMenuAbout.setVisibility(View.GONE);
                layoutMenuHelp.setVisibility(View.GONE);
                layoutNGRA.setVisibility(View.GONE);
                layoutMenuExit.setVisibility(View.GONE);

            }
        }, 450);

    }//_____________________________________________________________________________________________ End CloseMenu


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {//_________________________________________________________________ Start onRequestPermissionsResult

    }//_____________________________________________________________________________________________ End onRequestPermissionsResult


    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext


    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed

        if (NewRequestFragment.InProcess) {
            Toast.makeText(this, getResources().getString(R.string.InProcess), Toast.LENGTH_SHORT).show();
            return;
        }


        if (layoutMenu.getVisibility() == View.VISIBLE) {
            CloseMenu();
            return;
        }


        NavDestination navDestination = navController.getCurrentDestination();
        String fragment = navDestination.getLabel().toString();
        if ((!fragment.equalsIgnoreCase("fragment_before_login")) &&
                (!fragment.equalsIgnoreCase("fragment_home"))) {
            super.onBackPressed();
            return;
        }


        if (doubleBackToExitPressedOnce) {
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج 2 بار کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }//_____________________________________________________________________________________________ End onBackPressed


}
