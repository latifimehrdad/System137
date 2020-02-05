package com.ngra.system137.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.ngra.system137.R;
import com.ngra.system137.databinding.FragmentNewRequestBinding;
import com.ngra.system137.models.ModelNewRequest;
import com.ngra.system137.models.ModelSpinnerItem;
import com.ngra.system137.utility.MaterialShowCaseView.MaterialShowcaseSequence;
import com.ngra.system137.utility.MaterialShowCaseView.ShowcaseConfig;
import com.ngra.system137.utility.StaticFunctions;
import com.ngra.system137.viewmodels.fragments.VM_MapFragment;
import com.ngra.system137.viewmodels.fragments.VM_NewRequest;
import com.ngra.system137.views.adabters.FilesAdabter;
import com.ngra.system137.views.dialogs.DialogMessage;
import com.ngra.system137.views.dialogs.DialogProgress;
import com.ngra.system137.views.dialogs.searchspinner.MLSpinnerDialog;
import com.ngra.system137.views.dialogs.searchspinner.OnSpinnerItemClick;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
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
    private FilesAdabter filesAdabter;
    private ModelNewRequest newRequest;
    private int REQUEST_PICK_Gallery = 7126;
    private int REQUEST_PICK_Video = 7127;
    public static boolean InProcess;
    private byte RecordeVoice;
    private Handler handlerRecorde;
    private Runnable runnableRecord;
    private MediaRecorder myAudioRecorder;
    private String PathRecorcVoice;
    private MediaPlayer mediaPlayer;
    public static boolean ShowLayoutAttachfile = false;

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

    @BindView(R.id.EditSubject)
    EditText EditSubject;

    @BindView(R.id.EditName)
    EditText EditName;

    @BindView(R.id.EditFamily)
    EditText EditFamily;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.layoutChooseAddress)
    LinearLayout layoutChooseAddress;

    @BindView(R.id.layoutAttach)
    LinearLayout layoutAttach;

    @BindView(R.id.RecyclerFiles)
    RecyclerView RecyclerFiles;

    @BindView(R.id.layoutDialog)
    LinearLayout layoutDialog;

    @BindView(R.id.layoutChooseDoc)
    LinearLayout layoutChooseDoc;

    @BindView(R.id.layoutChooseImage)
    LinearLayout layoutChooseImage;

    @BindView(R.id.layoutChooseVideo)
    LinearLayout layoutChooseVideo;

    @BindView(R.id.ButtonClose)
    Button ButtonClose;

    @BindView(R.id.layoutRequest)
    LinearLayout layoutRequest;

    @BindView(R.id.layoutProgressVideo)
    LinearLayout layoutProgressVideo;

    @BindView(R.id.txtVideoProgress)
    TextView txtVideoProgress;

    @BindView(R.id.layoutRecordVoice)
    LinearLayout layoutRecordVoice;

    @BindView(R.id.layoutRecord)
    LinearLayout layoutRecord;


    @BindView(R.id.imgRecordVoice)
    ImageView imgRecordVoice;

    @BindView(R.id.progressRecordVoice)
    ProgressBar progressRecordVoice;

    @BindView(R.id.imgPlayVoice)
    ImageView imgPlayVoice;

    @BindView(R.id.imgRecordAdd)
    ImageView imgRecordAdd;

    @BindView(R.id.imgRecordDelete)
    ImageView imgRecordDelete;


    @BindView(R.id.layoutShowFiles)
    LinearLayout layoutShowFiles;

    @BindView(R.id.TextCountFile)
    TextView TextCountFile;


    @BindView(R.id.imgCountFile)
    ImageView imgCountFile;

    @BindView(R.id.layoutRecordProgress)
    LinearLayout layoutRecordProgress;

    @BindView(R.id.layoutRecordAction)
    LinearLayout layoutRecordAction;

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
        layoutDialog.setVisibility(View.GONE);
        layoutRequest.setVisibility(View.VISIBLE);
        layoutProgressVideo.setVisibility(View.GONE);
        layoutRecord.setVisibility(View.GONE);
        InProcess = false;
        ShowLayoutAttachfile = false;
        CheckLogin();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Login)
                    ShowCaseHelp();
            }
        }, 500);
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

        SetTextWatcher();
        HideFiles();

    }//_____________________________________________________________________________________________ End onStart


    private void ShowCaseHelp() {//_________________________________________________________________ Start ShowCaseHelp

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(100); // half second between each showcase view

        String SHOWCASE_ID = "sequence request";
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

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


    }//_____________________________________________________________________________________________ End ShowCaseHelp


    private void ShowCaseHelpFiles() {//____________________________________________________________ Start ShowCaseHelpFiles

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(100); // half second between each showcase view

        String SHOWCASE_ID = "sequence files";
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

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


    private void SetAddress() {//___________________________________________________________________ Start SetAddress
        vm_newRequest.SetAddress();
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
                            case "GetAddress":
                                EditAddress.setText(vm_newRequest.getTextAddress());
                                break;
                            case "RemoveFile":
                                HideFiles();
                                break;
                            case "RepetitiousFile":
                                ShowMessage(
                                        context.getResources().getString(R.string.RepetitiousFile),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "AddFile":
                                InProcess = false;
                                layoutDialog.setVisibility(View.GONE);
                                layoutRequest.setVisibility(View.VISIBLE);
                                layoutProgressVideo.setVisibility(View.GONE);
                                HideFiles();
                                break;
                            case "MaxFileChoose1":
                                ShowMessage(
                                        context.getResources().getString(R.string.MaxFileChoose1),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "MaxFileChoose2":
                                ShowMessage(
                                        context.getResources().getString(R.string.MaxFileChoose2),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "MaxFileChoose3":
                                InProcess = false;
                                layoutProgressVideo.setVisibility(View.GONE);
                                ShowMessage(
                                        context.getResources().getString(R.string.MaxFileChoose3),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "MaxFileChoose4":
                                ShowMessage(
                                        context.getResources().getString(R.string.MaxFileChoose4),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "OverSize1":
                                ShowMessage(
                                        context.getResources().getString(R.string.OverSize1),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "OverSize2":
                                ShowMessage(
                                        context.getResources().getString(R.string.OverSize2),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "OverSize3":
                                InProcess = false;
                                layoutProgressVideo.setVisibility(View.GONE);
                                ShowMessage(
                                        context.getResources().getString(R.string.OverSize3),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
                                break;
                            case "onProgress":
                                txtVideoProgress.setText(vm_newRequest.getVideoProgress() + " %");
                                break;
                            case "MaxFileCount":
                                InProcess = false;
                                layoutProgressVideo.setVisibility(View.GONE);
                                ShowMessage(
                                        context.getResources().getString(R.string.MaxFileCount),
                                        getResources().getColor(R.color.ML_White),
                                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                                );
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SetAddress();
            }
        }, 500);


    }//_____________________________________________________________________________________________ End ObserverObservables


    private void ShowFiles() {//____________________________________________________________________ Start ShowFiles

        if (RecyclerFiles.getAdapter() == null) {
            SetAdabterFiles();
            imgCountFile.setImageResource(R.drawable.ic_keyboard_arrow_up);
            TextCountFile.setText(
                    context.getResources().getString(R.string.FilesChoose) +
                            " " +
                            vm_newRequest.FileCount() +
                            " ، " +
                            context.getResources().getString(R.string.Hidden)
            );
        } else
            filesAdabter.notifyDataSetChanged();

    }//_____________________________________________________________________________________________ End ShowFiles


    private void HideFiles() {//____________________________________________________________________ Start HideFiles

        RecyclerFiles.setAdapter(null);
        imgCountFile.setImageResource(R.drawable.ic_keyboard_arrow_down);
        TextCountFile.setText(
                context.getResources().getString(R.string.FilesChoose) +
                        " " +
                        vm_newRequest.FileCount() +
                        " ، " +
                        context.getResources().getString(R.string.Show)
        );

    }//_____________________________________________________________________________________________ End HideFiles


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditDescription.addTextChangedListener(TextChangeForChangeBack(EditDescription));
        EditAddress.addTextChangedListener(TextChangeForChangeBack(EditAddress));
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
        EditFamily.addTextChangedListener(TextChangeForChangeBack(EditFamily));
        EditName.addTextChangedListener(TextChangeForChangeBack(EditName));
        EditSubject.addTextChangedListener(TextChangeForChangeBack(EditSubject));
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

        layoutShowFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecyclerFiles.getAdapter() == null)
                    ShowFiles();
                else
                    HideFiles();
            }
        });

        layoutRecordVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InProcess) {
                    ShowMessage(
                            context.getResources().getString(R.string.InProcess),
                            getResources().getColor(R.color.ML_White),
                            context.getResources().getDrawable(R.drawable.ic_warning_red)
                    );
                    return;
                }
                if (RecordeVoice == 1)
                    return;


                File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                        context.getResources().getString(R.string.FolderName));
                PathRecorcVoice = fileOrDirectory.getPath() + "/record_voice.mp3";

                if (!vm_newRequest.CheckRepetitiousFile(PathRecorcVoice, 4, 1)) {
                    SetColorForStartRecordVoice();
                    layoutRecord.setVisibility(View.VISIBLE);
                    layoutRecordProgress.setVisibility(View.GONE);
                    layoutRecordAction.setVisibility(View.GONE);
                    imgPlayVoice.setVisibility(View.GONE);
                    imgRecordDelete.setVisibility(View.GONE);
                }

            }
        });


        imgRecordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecordeVoice == 2 &&
                        PathRecorcVoice != null &&
                        !PathRecorcVoice.equalsIgnoreCase("null")) {
                    vm_newRequest.AddFile(PathRecorcVoice, 4);
                }

            }
        });


        imgRecordDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecordeVoice == 2 &&
                        PathRecorcVoice != null &&
                        !PathRecorcVoice.equalsIgnoreCase("null")) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    File f = new File(PathRecorcVoice);
                    if (f.exists())
                        f.delete();

                    SetColorForStartRecordVoice();
                }

            }
        });


        imgPlayVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecordeVoice != 2)
                    return;

                if (mediaPlayer == null)
                    mediaPlayer = new MediaPlayer();
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer = null;
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(PathRecorcVoice);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    // make something
                }
            }
        });

        imgRecordVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecordeVoice != 1)
                    StartRecordVoice();
                else
                    StopRecordVoice();
            }
        });

        layoutProgressVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutRecord.setVisibility(View.GONE);
                ShowMessage(
                        context.getResources().getString(R.string.InProcess),
                        getResources().getColor(R.color.ML_White),
                        context.getResources().getDrawable(R.drawable.ic_warning_red)
                );
            }
        });

        layoutChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InProcess) {
                    ShowMessage(
                            context.getResources().getString(R.string.InProcess),
                            getResources().getColor(R.color.ML_White),
                            context.getResources().getDrawable(R.drawable.ic_warning_red)
                    );
                    return;
                }
                layoutRecord.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "انتخاب ویدیو"), REQUEST_PICK_Video);
            }
        });


        layoutChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InProcess) {
                    ShowMessage(
                            context.getResources().getString(R.string.InProcess),
                            getResources().getColor(R.color.ML_White),
                            context.getResources().getDrawable(R.drawable.ic_warning_red)
                    );
                    return;
                }
                layoutRecord.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "انتخاب عکس"), REQUEST_PICK_Gallery);
            }
        });


        ButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InProcess) {
                    ShowMessage(
                            context.getResources().getString(R.string.InProcess),
                            getResources().getColor(R.color.ML_White),
                            context.getResources().getDrawable(R.drawable.ic_warning_red)
                    );
                    return;
                }
                layoutRecord.setVisibility(View.GONE);
                layoutDialog.setVisibility(View.GONE);
                layoutRequest.setVisibility(View.VISIBLE);
            }
        });

        layoutChooseDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InProcess) {
                    ShowMessage(
                            context.getResources().getString(R.string.InProcess),
                            getResources().getColor(R.color.ML_White),
                            context.getResources().getDrawable(R.drawable.ic_warning_red)
                    );
                    return;
                }
                layoutRecord.setVisibility(View.GONE);
                DialogProperties properties = new DialogProperties();
                properties.selection_mode = DialogConfigs.SINGLE_MODE;
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
                        vm_newRequest.AddFile(files[0], 1);
                    }
                });

                dialog.show();
            }
        });

        layoutAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layoutDialog.getVisibility() == View.VISIBLE) {
                    layoutDialog.setVisibility(View.GONE);
                    layoutRequest.setVisibility(View.VISIBLE);
                    ShowLayoutAttachfile = false;
                } else {
                    layoutDialog.setVisibility(View.VISIBLE);
                    layoutRequest.setVisibility(View.INVISIBLE);
                    layoutRecord.setVisibility(View.GONE);
                    ShowLayoutAttachfile = true;
                    ShowCaseHelpFiles();
                }
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
                if (CheckEmpty()) {
                    ShowLoading();
                    ShowLoading();
                    vm_newRequest.SendNewRequest(newRequest);
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private void SetColorForStartRecordVoice() {//__________________________________________________ Start SetColorForStartRecordVoice

        Animation bounce = AnimationUtils.loadAnimation(context, R.anim.bounce);
        imgRecordVoice.setAnimation(bounce);
        imgRecordVoice.setImageResource(R.drawable.ic_record);
        imgRecordVoice
                .setColorFilter(
                        ContextCompat.getColor(context, R.color.colorAccent),
                        android.graphics.PorterDuff.Mode.SRC_IN);

        imgPlayVoice.setColorFilter(
                ContextCompat.getColor(context, R.color.ML_Primary),
                android.graphics.PorterDuff.Mode.SRC_IN);

        imgRecordAdd.setColorFilter(
                ContextCompat.getColor(context, R.color.ML_Primary),
                android.graphics.PorterDuff.Mode.SRC_IN);

        imgRecordDelete.setColorFilter(
                ContextCompat.getColor(context, R.color.ML_Primary),
                android.graphics.PorterDuff.Mode.SRC_IN);

        layoutRecordAction.setVisibility(View.GONE);
        imgPlayVoice.setVisibility(View.GONE);
        imgRecordDelete.setVisibility(View.GONE);

        progressRecordVoice.setMax(60);
        progressRecordVoice.setProgress(0);
        RecordeVoice = 0;
    }//_____________________________________________________________________________________________ End SetColorForStartRecordVoice

    private void StartRecordVoice() {//_____________________________________________________________ Start StartRecordVoice

        layoutRecordProgress.setVisibility(View.VISIBLE);

        File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.FolderName));
        if (!fileOrDirectory.exists())
            fileOrDirectory.mkdir();

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
        }

        PathRecorcVoice = fileOrDirectory.getPath() + "/record_voice.mp3";
        File f = new File(PathRecorcVoice);
        if (f.exists())
            f.delete();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myAudioRecorder == null) {
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(PathRecorcVoice);
                }

                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    SetColorForStartRecordVoice();
                } catch (IOException ioe) {
                    SetColorForStartRecordVoice();
                }

                SetColorForStartRecordVoice();
                InProcess = true;
                RecordeVoice = 1;
                imgRecordVoice.setImageResource(R.drawable.ic_stop);
                imgRecordVoice
                        .setColorFilter(
                                ContextCompat.getColor(context, R.color.ML_PrimaryDark),
                                android.graphics.PorterDuff.Mode.SRC_IN);

                Animation bounce = AnimationUtils.loadAnimation(context, R.anim.heart_beat);
                imgRecordVoice.setAnimation(bounce);

                handlerRecorde = new Handler();
                runnableRecord = new Runnable() {
                    @Override
                    public void run() {
                        progressRecordVoice.setProgress(progressRecordVoice.getProgress() + 1);
                        if (progressRecordVoice.getProgress() < 61)
                            handlerRecorde.postDelayed(this, 1000);
                        else
                            StopRecordVoice();
                    }
                };

                handlerRecorde.postDelayed(runnableRecord, 1);
            }
        }, 100);


    }//_____________________________________________________________________________________________ End StartRecordVoice


    private void SetColorForStopRecordVoice() {//___________________________________________________ Start SetColorForStopRecordVoice
        Animation bounce = AnimationUtils.loadAnimation(context, R.anim.bounce);
        imgRecordVoice.setAnimation(bounce);
        imgRecordVoice.setImageResource(R.drawable.ic_record);
        imgRecordVoice
                .setColorFilter(
                        ContextCompat.getColor(context, R.color.colorAccent),
                        android.graphics.PorterDuff.Mode.SRC_IN);

        imgPlayVoice.setColorFilter(
                ContextCompat.getColor(context, R.color.colorAccent),
                android.graphics.PorterDuff.Mode.SRC_IN);

        imgRecordAdd.setColorFilter(
                ContextCompat.getColor(context, R.color.ML_Confirm),
                android.graphics.PorterDuff.Mode.SRC_IN);

        imgRecordDelete.setColorFilter(
                ContextCompat.getColor(context, R.color.ML_PrimaryDark),
                android.graphics.PorterDuff.Mode.SRC_IN);

        layoutRecordAction.setVisibility(View.VISIBLE);
        layoutRecordProgress.setVisibility(View.GONE);
        imgPlayVoice.setVisibility(View.VISIBLE);
        imgRecordDelete.setVisibility(View.VISIBLE);

        progressRecordVoice.setMax(60);
        progressRecordVoice.setProgress(0);
        RecordeVoice = 2;
    }//_____________________________________________________________________________________________ End SetColorForStopRecordVoice


    private void StopRecordVoice() {//______________________________________________________________ Start StopRecordVoice

        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        InProcess = false;
        handlerRecorde.removeCallbacks(runnableRecord);
        handlerRecorde = null;
        runnableRecord = null;
        SetColorForStopRecordVoice();

    }//_____________________________________________________________________________________________ End StopRecordVoice


    private void SetAdabterFiles() {//______________________________________________________________ Start SetAdabterFiles

        filesAdabter = new FilesAdabter(vm_newRequest.getFiles(), vm_newRequest);
        RecyclerFiles.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        RecyclerFiles.setAdapter(filesAdabter);

    }//_____________________________________________________________________________________________ End SetAdabterFiles


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
        boolean subject = true;

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

        if (EditSubject.getText().length() == 0) {
            EditSubject.setBackgroundResource(R.drawable.edit_empty_background);
            EditSubject.setError(getResources().getString(R.string.EmptySubject));
            EditSubject.requestFocus();
            subject = false;
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
            if (type && description && address && subject)
                return true;
            else
                return false;
        } else {
            if (mobile && family && name && type && description && address && subject)
                return true;
            else
                return false;
        }

    }//_____________________________________________________________________________________________ End CheckEmpty


    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        VM_MapFragment.address = null;
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ End onDestroy

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//__________________ Start onActivityResult
        Log.i("meri", "ChooseFile");
        if (resultCode == RESULT_OK && requestCode == REQUEST_PICK_Gallery) {
            Uri uri = data.getData();
            String file = vm_newRequest.GetPathFromUri(uri);
            vm_newRequest.AddFile(file, 2);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_PICK_Video) {
            InProcess = true;
            layoutProgressVideo.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            String file = vm_newRequest.GetPathFromUri(uri);
            vm_newRequest.AddFile(file, 3);
        }
    }//_____________________________________________________________________________________________ End onActivityResult

}
