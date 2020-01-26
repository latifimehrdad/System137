package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;

import com.ngra.system137.R;
import com.ngra.system137.models.ModelNewRequest;
import com.ngra.system137.models.ModelSpinnerItem;
import com.ngra.system137.utility.StaticFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.reactivex.subjects.PublishSubject;

public class VM_NewRequest {

    private Context context;
    private PublishSubject<String> Observables = null;
    private ArrayList<ModelSpinnerItem> types;
    private ModelNewRequest newRequest;

    public VM_NewRequest(Context context) {//_______________________________________________________ Start VM_NewRequest
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_NewRequest


    public void GetType() {//_______________________________________________________________________ Start GetType
        types = new ArrayList<>();
        ModelSpinnerItem item = new ModelSpinnerItem(1, "نوع اول");
        types.add(item);
        Observables.onNext("GetType");
    }//_____________________________________________________________________________________________ End GetType


    public void SendNewRequest(ModelNewRequest modelNewRequest) {//_________________________________ Start SendRequest

        this.newRequest = modelNewRequest;
        DeleteFileAndFolder();
        if (newRequest.getFiles().size() > 0)
            CopyFiles();
        else
            SendRequestNon();
    }//_____________________________________________________________________________________________ End SendRequest


    private void DeleteFileAndFolder() {//__________________________________________________________ Start DeleteFileAndFolder
        File zipFile = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.ZipFolder));

        if (zipFile.exists())
            zipFile.delete();
        File myDirectory = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.FolderName));
        if (myDirectory.exists())
            deleteRecursive(myDirectory);
    }//_____________________________________________________________________________________________ End DeleteFileAndFolder


    void deleteRecursive(File fileOrDirectory) {//__________________________________________________ Start deleteRecursive
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }//_____________________________________________________________________________________________ End deleteRecursive


    private void CopyFiles() {//____________________________________________________________________ Start CopyFiles

        File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.FolderName));

        fileOrDirectory.mkdirs();
        String Path = fileOrDirectory.getPath() + "/";
        for (String file : newRequest.getFiles()) {
            if (StaticFunctions.isCancel) {
                break;
            }
            String filename = file.substring(file.lastIndexOf("/") + 1);
            copyFile(file, filename, Path);
        }

        File zipFile = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.ZipFolder));

        zipFolder(Path, zipFile.getPath());


    }//_____________________________________________________________________________________________ End CopyFiles


    private void zipFolder(String inputFolderPath, String outZipPath) {//___________________________ Start zipFolder
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            SendRequestFiles();
        } catch (IOException ioe) {
        }
    }//_____________________________________________________________________________________________ End zipFolder


    private void SendRequestFiles() {//_____________________________________________________________ Start SendRequestFiles

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeleteFileAndFolder();
                Observables.onNext("SendOk");
            }
        }, 5000);
    }//_____________________________________________________________________________________________ End SendRequestFiles


    private void SendRequestNon() {//_______________________________________________________________ Start SendRequestNon

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeleteFileAndFolder();
                Observables.onNext("SendOk");
            }
        }, 5000);
    }//_____________________________________________________________________________________________ End SendRequestNon


    private void copyFile(String inputPath, String inputFile, String outputPath) {//________________ Start copyFile

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                if (StaticFunctions.isCancel) {
                    break;
                }
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {

        } catch (Exception e) {
        }
    }//_____________________________________________________________________________________________ End copyFile


    public ArrayList<ModelSpinnerItem> getTypes() {//_______________________________________________ Start getTypes
        return types;
    }//_____________________________________________________________________________________________ End getTypes


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables


    public boolean CheckUserLogin() {//_____________________________________________________________ Start CheckUserLogin

        SharedPreferences prefs = context.getSharedPreferences("system137token", 0);
        if (prefs == null) {
            return false;
        } else {
            boolean login = prefs.getBoolean("login", false);
            if (login)
                return true;
            else
                return false;
        }

    }//_____________________________________________________________________________________________ End CheckUserLogin

}
