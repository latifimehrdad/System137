package com.ngra.system137.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.ngra.system137.R;
import com.ngra.system137.backgroung.BackgroundServiceLocation;
import com.ngra.system137.databinding.FragmentNewRequestBinding;
import com.ngra.system137.models.ModelGetAddress;
import com.ngra.system137.models.ModelNewRequest;
import com.ngra.system137.models.ModelSpinnerItem;
import com.ngra.system137.utility.StaticFunctions;
import com.ngra.system137.viewmodels.fragments.VM_MapFragment;
import com.ngra.system137.viewmodels.fragments.VM_NewRequest;
import com.ngra.system137.views.adabters.FilesAdabter;
import com.ngra.system137.views.dialogs.DialogMessage;
import com.ngra.system137.views.dialogs.DialogProgress;
import com.ngra.system137.views.dialogs.searchspinner.MLSpinnerDialog;
import com.ngra.system137.views.dialogs.searchspinner.OnSpinnerItemClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ngra.system137.utility.StaticFunctions.TextChangeForChangeBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {

    private Context context;
    private VM_NewRequest vm_newRequest;
    private View view;
    private boolean ClickType = false;
    private ArrayList<ModelSpinnerItem> TypeList;
    private DisposableObserver<String> observer;
    private DialogProgress progress;
    private MLSpinnerDialog spinnerTypes;
    private int TypeId = -1;
    private boolean Login;
    private NavController navController;
    private List<String> Files;
    private FilesAdabter filesAdabter;
    private ModelNewRequest newRequest;

    @BindView(R.id.layoutGuest)
    LinearLayout layoutGuest;

    @BindView(R.id.SpinnerType)
    LinearLayout SpinnerType;

    @BindView(R.id.SpinnerText)
    TextView SpinnerText;

    @BindView(R.id.EditDescription)
    EditText EditDescription;

    @BindView(R.id.EditAddress)
    EditText EditAddress;


    @BindView(R.id.ButtonSend)
    RelativeLayout ButtonSend;

    @BindView(R.id.imgSend)
    ImageView imgSend;

    @BindView(R.id.BtnSendText)
    TextView BtnSendText;

    @BindView(R.id.ProgressGif)
    GifView ProgressGif;


    @BindView(R.id.EditName)
    EditText EditName;

    @BindView(R.id.EditFamily)
    EditText EditFamily;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.layoutChooseAddress)
    LinearLayout layoutChooseAddress;

    @BindView(R.id.layoutChooseFile)
    LinearLayout layoutChooseFile;

    @BindView(R.id.RecyclerFiles)
    RecyclerView RecyclerFiles;


    public NewRequestFragment() {//_________________________________________________________________ Start NewRequestFragment

    }//_____________________________________________________________________________________________ End NewRequestFragment


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_newRequest = new VM_NewRequest(context);
        FragmentNewRequestBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_new_request, container, false
        );
        binding.setNewrequest(vm_newRequest);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        newRequest = new ModelNewRequest();
        if (observer != null)
            observer.dispose();
        observer = null;
        SpinnerText.setText(getResources().getString(R.string.ChooseType));
        ObserverObservables();
        SetClick();
        DismissLoading();
        CheckLogin();
        SetTextWatcher();
        SetAddress();
        Files = new ArrayList<>();
        Files.clear();

    }//_____________________________________________________________________________________________ End onStart


    private void SetAddress() {//___________________________________________________________________ Start SetAddress
        ModelGetAddress GetAddress = VM_MapFragment.address;
        if (GetAddress != null && GetAddress.getAddress() != null) {
            StringBuilder address = new StringBuilder();

            String country = GetAddress.getAddress().getCountry();
            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {
                address.append(country);
                address.append(" ");
            }

            String state = GetAddress.getAddress().getState();
            if (state != null &&
                    !state.equalsIgnoreCase("null") &&
                    !state.equalsIgnoreCase("")) {
                address.append(state);
                address.append(" ");
            }

            String county = GetAddress.getAddress().getCounty();
            if (county != null &&
                    !county.equalsIgnoreCase("null") &&
                    !county.equalsIgnoreCase("")) {
                address.append(county);
                address.append(" ");
            }

            String city = GetAddress.getAddress().getCity();
            if (city != null &&
                    !city.equalsIgnoreCase("null") &&
                    !city.equalsIgnoreCase("")) {
                address.append("شهر");
                address.append(" ");
                address.append(city);
                address.append(" ");
            }

            String neighbourhood = GetAddress.getAddress().getNeighbourhood();
            if (neighbourhood != null &&
                    !neighbourhood.equalsIgnoreCase("null") &&
                    !neighbourhood.equalsIgnoreCase("")) {
                address.append(neighbourhood);
                address.append(" ");
            }

            String suburb = GetAddress.getAddress().getSuburb();
            if (suburb != null &&
                    !suburb.equalsIgnoreCase("null") &&
                    !suburb.equalsIgnoreCase("") &&
                    !suburb.equalsIgnoreCase(neighbourhood)) {
                address.append(suburb);
                address.append(" ");
            }

            String road = GetAddress.getAddress().getRoad();
            if (road != null &&
                    !road.equalsIgnoreCase("null") &&
                    !road.equalsIgnoreCase("")) {
                address.append("خیابان");
                address.append(" ");
                address.append(road);
                address.append(" ");
            }

            EditAddress.setText(address.toString());
        } else
            EditAddress.setText("");
    }//_____________________________________________________________________________________________ End SetAddress


    private void CheckLogin() {//___________________________________________________________________ Start CheckLogin
        Login = vm_newRequest.CheckUserLogin();
        if (Login) {
            layoutGuest.setVisibility(View.GONE);
        } else {
            layoutGuest.setVisibility(View.VISIBLE);
        }
    }//_____________________________________________________________________________________________ End CheckLogin


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DismissProgress();
                        switch (s) {
                            case "GetType":
                                TypeList = vm_newRequest.getTypes();
                                SetItemTypes();
                                break;
                            case "SendOk":
                                getActivity().onBackPressed();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        vm_newRequest
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservables


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditDescription.addTextChangedListener(TextChangeForChangeBack(EditDescription));
        EditAddress.addTextChangedListener(TextChangeForChangeBack(EditAddress));
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
        EditFamily.addTextChangedListener(TextChangeForChangeBack(EditFamily));
        EditName.addTextChangedListener(TextChangeForChangeBack(EditName));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void SetItemTypes() {//_________________________________________________________________ Start SetItemTypes
        SpinnerText.setText(getResources().getString(R.string.ChooseType));
        TypeId = -1;
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerTypes = new MLSpinnerDialog(
                getActivity(),
                TypeList,
                getResources().getString(R.string.SearchTypes),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Cancel));// With 	Animation
        spinnerTypes.setCancellable(true); // for cancellable
        spinnerTypes.setShowKeyboard(false);// for open keyboard by default
        spinnerTypes.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                SpinnerText.setText(item);
                TypeId = TypeList.get(position).getId();
                SpinnerText.setBackgroundResource(R.drawable.back_spinner);
            }
        });

        if (ClickType)
            spinnerTypes.showSpinerDialog();
    }//_____________________________________________________________________________________________ End SetItemTypes


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        layoutChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogProperties properties = new DialogProperties();
                properties.selection_mode = DialogConfigs.MULTI_MODE;
                properties.selection_type = DialogConfigs.FILE_SELECT;
                properties.root = new File(DialogConfigs.DEFAULT_DIR);
                properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
                properties.offset = new File(DialogConfigs.DEFAULT_DIR);
                properties.extensions = null;
                FilePickerDialog dialog = new FilePickerDialog(context, properties);
                dialog.setTitle(getResources().getString(R.string.ChooseFile));
                dialog.setNegativeBtnName(getResources().getString(R.string.Cancel));
                dialog.setPositiveBtnName(getResources().getString(R.string.Choose));
                dialog.setDialogSelectionListener(new DialogSelectionListener() {
                    @Override
                    public void onSelectedFilePaths(String[] files) {

                        for (String file : files) {
                            Files.add(file);
                        }
                        SetAdabter();

                    }
                });

                dialog.show();
            }
        });


        layoutChooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });


        SpinnerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickType = true;
//                if ((TypeList == null) || (TypeList.size() == 0))
                if (TypeList == null)
                    GetTypes();
                else
                    spinnerTypes.showSpinerDialog();
            }
        });

        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (CheckEmpty()) {
//                    ShowLoading();
                newRequest.setFiles(Files);
                ShowLoading();
                    vm_newRequest.SendNewRequest(newRequest);
//                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private void SetAdabter() {//___________________________________________________________________ Start SetAdabter

        filesAdabter = new FilesAdabter(Files, NewRequestFragment.this);
        RecyclerFiles.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        RecyclerFiles.setAdapter(filesAdabter);

    }//_____________________________________________________________________________________________ End SetAdabter


    public void checkLocationPermission() {//_______________________________________________________ Start checkLocationPermission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(context)
                        .setTitle("دسترسی به موقعیت")
                        .setMessage("برای نمایش مکان شما به موقعیت دسترسی بدهید")
                        .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        } else
            navController.navigate(R.id.action_newRequestFragment_to_mapFragment);
    }//_____________________________________________________________________________________________ End checkLocationPermission


    private void GetTypes() {//_____________________________________________________________________ Start GetTypes
        ShowProgressDialog();
        vm_newRequest.GetType();
    }//_____________________________________________________________________________________________ End GetTypes


    private void ShowProgressDialog() {//___________________________________________________________ Start ShowProgressDialog
        progress = new DialogProgress(context, null);
        progress.setCancelable(false);
        progress.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        DialogMessage dialogMessage = new DialogMessage(context, message, color, icon);
        dialogMessage.setCancelable(false);
        dialogMessage.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);

    }//_____________________________________________________________________________________________ End ShowMessage


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        StaticFunctions.isCancel = true;
        BtnSendText.setText(getResources().getString(R.string.SendRequest));
        ButtonSend.setBackground(getResources().getDrawable(R.drawable.button_bg));
        ProgressGif.setVisibility(View.GONE);
        imgSend.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        StaticFunctions.isCancel = false;
        BtnSendText.setText(getResources().getString(R.string.Cancel));
        ButtonSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        ProgressGif.setVisibility(View.VISIBLE);
        imgSend.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading


    private void DismissProgress() {//______________________________________________________________ Start DismissProgress
        if (progress != null)
            progress.dismiss();
    }//_____________________________________________________________________________________________ End DismissProgress


    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

        boolean mobile = true;
        boolean family = true;
        boolean name = true;
        boolean type = true;
        boolean description = true;
        boolean address = true;

        if (EditAddress.getText().length() == 0) {
            EditAddress.setBackgroundResource(R.drawable.edit_empty_background);
            EditAddress.setError(getResources().getString(R.string.EmptyAddress));
            EditAddress.requestFocus();
            address = false;
        }

        if (EditDescription.getText().length() == 0) {
            EditDescription.setBackgroundResource(R.drawable.edit_empty_background);
            EditDescription.setError(getResources().getString(R.string.EmptyDescription));
            EditDescription.requestFocus();
            description = false;
        }

        if (TypeId == -1) {
            SpinnerText.setBackgroundResource(R.drawable.edit_empty_background);
            type = false;
        }

        if (!Login) {
            if (EditPhoneNumber.getText().length() != 11) {
                EditPhoneNumber.setBackgroundResource(R.drawable.edit_empty_background);
                EditPhoneNumber.setError(getResources().getString(R.string.EmptyMobile));
                EditPhoneNumber.requestFocus();
                mobile = false;
            } else {
                String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
                if (!ZeroNine.equalsIgnoreCase("09")) {
                    EditPhoneNumber.setBackgroundResource(R.drawable.edit_empty_background);
                    EditPhoneNumber.setError(getResources().getString(R.string.EmptyMobile));
                    EditPhoneNumber.requestFocus();
                    mobile = false;
                }
            }


            if (EditFamily.getText().length() == 0) {
                EditFamily.setBackgroundResource(R.drawable.edit_empty_background);
                EditFamily.setError(getResources().getString(R.string.EmptyFamily));
                EditFamily.requestFocus();
                family = false;
            }


            if (EditName.getText().length() == 0) {
                EditName.setBackgroundResource(R.drawable.edit_empty_background);
                EditName.setError(getResources().getString(R.string.EmptyName));
                EditName.requestFocus();
                name = false;
            }
        }


        if (Login) {
            if (type && description && address)
                return true;
            else
                return false;
        } else {
            if (mobile && family && name && type && description && address)
                return true;
            else
                return false;
        }

    }//_____________________________________________________________________________________________ End CheckEmpty



    public void DeleteFiles(int position) {//_______________________________________________________ Start DeleteFiles

        Files.remove(position);
        filesAdabter.notifyDataSetChanged();

    }//_____________________________________________________________________________________________ End DeleteFiles



    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        VM_MapFragment.address = null;
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ End onDestroy
}
