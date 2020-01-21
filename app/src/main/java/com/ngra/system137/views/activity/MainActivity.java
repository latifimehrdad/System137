package com.ngra.system137.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ngra.system137.R;
import com.ngra.system137.databinding.ActivityMainBinding;
import com.ngra.system137.viewmodels.activity.VM_MainActivity;

import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

    private VM_MainActivity vm_mainActivity;
    private NavController navController;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        vm_mainActivity = new VM_MainActivity(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(vm_mainActivity);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }//_____________________________________________________________________________________________ End onCreate


    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext



    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed

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
