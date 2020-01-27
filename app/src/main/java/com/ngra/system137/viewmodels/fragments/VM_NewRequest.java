package com.ngra.system137.viewmodels.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import com.ngra.system137.R;
import com.ngra.system137.models.ModelChooseFiles;
import com.ngra.system137.models.ModelGetAddress;
import com.ngra.system137.models.ModelNewRequest;
import com.ngra.system137.models.ModelSpinnerItem;
import com.ngra.system137.utility.StaticFunctions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.reactivex.subjects.PublishSubject;

import static com.ngra.system137.utility.StaticFunctions.CustomToastShow;

public class VM_NewRequest {

    private Context context;
    private PublishSubject<String> Observables = null;
    private ArrayList<ModelSpinnerItem> types;
    private ModelNewRequest newRequest;
    private List<ModelChooseFiles> Files;
    private String TextAddress;

    public VM_NewRequest(Context context) {//_______________________________________________________ Start VM_NewRequest
        this.context = context;
        Observables = PublishSubject.create();
        Files = new ArrayList<>();
        Files.clear();
        DeleteFileAndFolder();
    }//_____________________________________________________________________________________________ End VM_NewRequest


    public void GetType() {//_______________________________________________________________________ Start GetType
        types = new ArrayList<>();
        ModelSpinnerItem item = new ModelSpinnerItem(1, "نوع اول");
        types.add(item);
        Observables.onNext("GetType");
    }//_____________________________________________________________________________________________ End GetType


    public void SendNewRequest(ModelNewRequest modelNewRequest) {//_________________________________ Start SendRequest

//        this.newRequest = modelNewRequest;
//        DeleteFileAndFolder();
//        if (newRequest.getFiles().size() > 0)
//            CopyFiles();
//        else
//            SendRequestNon();
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


    private boolean CopyFiles(String file) {//______________________________________________________ Start CopyFiles

        File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.FolderName));

        fileOrDirectory.mkdirs();
        String Path = fileOrDirectory.getPath() + "/";
        String filename = file.substring(file.lastIndexOf("/") + 1);
        Path = Path + filename;
        File src = new File(file);
        File path = new File(Path);
        try {
            return copyFile(src, path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //return copyFile(file, filename, Path);

//        File zipFile = new File(Environment.getExternalStorageDirectory(),
//                context.getResources().getString(R.string.ZipFolder));
//        zipFolder(Path, zipFile.getPath());


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


    public boolean copyFile(File src, File dst) throws IOException {//______________________________ Start copyFile
        FileInputStream var2 = new FileInputStream(src);
        FileOutputStream var3 = new FileOutputStream(dst);
        byte[] var4 = new byte[1024];

        int var5;
        while ((var5 = var2.read(var4)) > 0) {
            var3.write(var4, 0, var5);
        }

        var2.close();
        var3.close();
        return true;
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


    public void AddFile(String file, int type) {//__________________________________________________ Start AddFile
        if (!CheckFile(file, type))
            Observables.onNext("AddFile");
    }//_____________________________________________________________________________________________ End AddFile


    private boolean CheckFile(String file, int type) {//___________________________________________ Start CheckFile

        if (Files.size() == 6)
            return true;

        switch (type) {
            case 1://_____ file
                if (CheckRepetitiousFile(file, type, 2))
                    return true;

                if (CheckFileSize(file, 1024))
                    return true;

                int file_size = SizeFile(file);
                if (CopyFiles(file))
                    Files.add(new ModelChooseFiles(file, 1, file_size));
                else
                    return true;

                break;
            case 2://_____ image
                if (CheckRepetitiousFile(file, type, 3))
                    return true;

                if (CheckFileSize(file, 3 * 1024))
                    return true;

                if (CopyFiles(file)) {
                    File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                            context.getResources().getString(R.string.FolderName));
                    String Path = fileOrDirectory.getPath() + "/";
                    String filename = file.substring(file.lastIndexOf("/") + 1);
                    file = Path + filename;
                    File f = new File(file);
                    if (f.exists())
                        ResizeImages(file);
                    else
                        return true;
                } else
                    return true;

                break;
            case 3://_____ video
                if (CheckRepetitiousFile(file, type, 1))
                    return true;

                if (CheckFileSize(file, 80 * 1024))
                    return true;

                if (CopyFiles(file)) {
                    File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                            context.getResources().getString(R.string.FolderName));
                    String Path = fileOrDirectory.getPath() + "/";
                    String filename = file.substring(file.lastIndexOf("/") + 1);
                    file = Path + filename;
                    File f = new File(file);
                    if (f.exists())
                        ResizeVideo(file);
                    else
                        return true;
                } else
                    return true;
                break;
        }


        return false;
    }//_____________________________________________________________________________________________ End CheckFile


    private boolean CheckRepetitiousFile(String file, int type, int maxCount) {//___________________ Start CheckRepetitiousFile

        boolean ret = false;
        int count = 0;

        for (ModelChooseFiles f : Files)
            if (file.equalsIgnoreCase(f.getFileName())) {
                Observables.onNext("RepetitiousFile");
                ret = true;
            } else {
                if (type == f.getType())
                    count = count + 1;

                if (count >= maxCount) {
                    ret = true;
                    Observables.onNext("MaxFileChoose"+type);
                    break;
                }
            }

        return ret;

    }//_____________________________________________________________________________________________ End CheckRepetitiousFile


    private boolean CheckFileSize(String file, int maxSize) {//_____________________________________ Start CheckFileSize
        File f = new File(file);
        int file_size = SizeFile(file);
        if (file_size > maxSize) {
            Observables.onNext("OverSize");
            return true;
        } else
            return false;
    }//_____________________________________________________________________________________________ End CheckFileSize


    private int SizeFile(String file) {//___________________________________________________________ Start SizeFile
        File f = new File(file);
        int file_size = Integer.parseInt(String.valueOf(f.length() / 1024));
        return file_size;
    }//_____________________________________________________________________________________________ End SizeFile


    private void ResizeImages(String file) {//______________________________________________________ Start ResizeImages
        try {
            File imgFileOrig = new File(file);
            Bitmap b = BitmapFactory.decodeFile(imgFileOrig.getAbsolutePath());
            int origWidth = b.getWidth();
            int origHeight = b.getHeight();

            final int destWidth = 1024;

            if (origWidth > destWidth) {
                int destHeight = origHeight / (origWidth / destWidth);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                Bitmap b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);
                b2.compress(Bitmap.CompressFormat.JPEG, 85, outStream);
                File f = new File(file);
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(outStream.toByteArray());
                fo.close();
                if (destWidth > destHeight) {
                    Bitmap bmp = BitmapFactory.decodeFile(file);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                    FileOutputStream fOut;
                    fOut = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                }

            }
            int file_size = SizeFile(file);
            Files.add(new ModelChooseFiles(file, 2, file_size));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//_____________________________________________________________________________________________ End ResizeImages


    private void ResizeVideo(String file) {//_______________________________________________________ Start ResizeVideo

    }//_____________________________________________________________________________________________ End ResizeVideo


    public String GetPathFromUri(Uri uri) {//_______________________________________________________ Start GetPathFromUri
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }//_____________________________________________________________________________________________ End GetPathFromUri


    public void SetAddress() {//___________________________________________________________________ Start SetAddress
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

            TextAddress = address.toString();
        } else
            TextAddress = "";

        Observables.onNext("GetAddress");
    }//_____________________________________________________________________________________________ End SetAddress


    public void DeleteFiles(int position) {//_______________________________________________________ Start DeleteFiles

        Files.remove(position);
        Observables.onNext("RemoveFile");

    }//_____________________________________________________________________________________________ End DeleteFiles


    public List<ModelChooseFiles> getFiles() {//____________________________________________________ Start getFiles
        return Files;
    }//_____________________________________________________________________________________________ End getFiles


    public String getTextAddress() {//______________________________________________________________ Start getTextAddress
        return TextAddress;
    }//_____________________________________________________________________________________________ End getTextAddress
}
