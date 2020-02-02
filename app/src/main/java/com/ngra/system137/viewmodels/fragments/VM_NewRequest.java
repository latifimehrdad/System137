package com.ngra.system137.viewmodels.fragments;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.ngra.system137.R;
import com.ngra.system137.models.ModelChooseFiles;
import com.ngra.system137.models.ModelGetAddress;
import com.ngra.system137.models.ModelNewRequest;
import com.ngra.system137.models.ModelSpinnerItem;
import com.ngra.system137.utility.VideoCompress.VideoCompress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.reactivex.subjects.PublishSubject;

public class VM_NewRequest {

    private Context context;
    private PublishSubject<String> Observables = null;
    private ArrayList<ModelSpinnerItem> types;
    private ModelNewRequest newRequest;
    private static List<ModelChooseFiles> Files;
    private String TextAddress;
    private int VideoProgress;

    public VM_NewRequest(Context context) {//_______________________________________________________ Start VM_NewRequest
        this.context = context;
        Observables = PublishSubject.create();
        if (Files == null)
            Files = new ArrayList<>();
        if (Files.size() == 0)
            DeleteFileAndFolder();
    }//_____________________________________________________________________________________________ End VM_NewRequest


    public void GetType() {//_______________________________________________________________________ Start GetType
        types = new ArrayList<>();
        ModelSpinnerItem item = new ModelSpinnerItem(1, "نوع اول");
        ModelSpinnerItem item2 = new ModelSpinnerItem(2, "نوع دوم");
        types.add(item);
        types.add(item2);
        Observables.onNext("GetType");
    }//_____________________________________________________________________________________________ End GetType


    public void SendNewRequest(ModelNewRequest modelNewRequest) {//_________________________________ Start SendRequest
        SendRequestFiles();
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
        if (!fileOrDirectory.exists())
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
                Files.clear();
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
                Files.clear();
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

        if (Files.size() == 6) {
            Observables.onNext("MaxFileCount");
            return true;
        }


        switch (type) {
            case 1://_____ file
                if (CheckRepetitiousFile(file, type, 2))
                    return true;

                if (CheckFileSize(file, 1024, type))
                    return true;

                int file_size = SizeFile(file);
                if (CopyFiles(file)) {
                    File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                            context.getResources().getString(R.string.FolderName));
                    if (!fileOrDirectory.exists())
                        fileOrDirectory.mkdirs();
                    String Path = fileOrDirectory.getPath() + "/";
                    String filename = file.substring(file.lastIndexOf("/") + 1);
                    file = Path + filename;
                    Files.add(new ModelChooseFiles(file, type, file_size));
                } else
                    return true;

                break;
            case 2://_____ image
                if (CheckRepetitiousFile(file, type, 3))
                    return true;

                if (CheckFileSize(file, 3 * 1024, type))
                    return true;

                if (CopyFiles(file)) {

                    File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                            context.getResources().getString(R.string.FolderName));
                    if (!fileOrDirectory.exists())
                        fileOrDirectory.mkdirs();
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

                if (CheckFileSize(file, 200 * 1024, type))
                    return true;

                File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                        context.getResources().getString(R.string.FolderName));
                if (!fileOrDirectory.exists())
                    fileOrDirectory.mkdirs();
                String Path = fileOrDirectory.getPath() + "/";
                String filename = file.substring(file.lastIndexOf("/") + 1);
                Path = Path + filename;
                File f = new File(file);
                if (f.exists())
                    ResizeVideo(file, Path);
                return true;

            case 4:

                if (CheckRepetitiousFile(file, type, 1))
                    return true;

                if (CheckFileSize(file, 3 * 1024, type))
                    return true;

                int size = SizeFile(file);
                Files.add(new ModelChooseFiles(file, type, size));

                break;
        }


        return false;
    }//_____________________________________________________________________________________________ End CheckFile


    public boolean CheckRepetitiousFile(String file, int type, int maxCount) {//____________________ Start CheckRepetitiousFile
        boolean ret = false;
        int count = 0;

        for (ModelChooseFiles f : Files)
            if (file.equalsIgnoreCase(f.getFileName())) {
                if (type != 4)
                    Observables.onNext("RepetitiousFile");
                else
                    Observables.onNext("MaxFileChoose" + type);
                ret = true;
            } else {
                if (type == f.getType())
                    count = count + 1;

                if (count >= maxCount) {
                    ret = true;
                    Observables.onNext("MaxFileChoose" + type);
                    break;
                }
            }

        return ret;

    }//_____________________________________________________________________________________________ End CheckRepetitiousFile


    private boolean CheckFileSize(String file, int maxSize, int type) {//___________________________ Start CheckFileSize
        File f = new File(file);
        int file_size = SizeFile(file);
        if (file_size > maxSize) {
            Observables.onNext("OverSize" + type);
            return true;
        } else
            return false;
    }//_____________________________________________________________________________________________ End CheckFileSize


    public int SizeFile(String file) {//___________________________________________________________ Start SizeFile
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


    private void ResizeVideo(String file, String path) {//_______________________________________________________ Start ResizeVideo
        VideoCompress.VideoCompressTask task = VideoCompress.compressVideoLow(
                file, path, new VideoCompress.CompressListener() {
                    @Override
                    public void onStart() {
                        //Start Compress
                    }

                    @Override
                    public void onSuccess() {
                        int file_size = SizeFile(path);
                        Files.add(new ModelChooseFiles(path, 3, file_size));
                        Observables.onNext("AddFile");
                    }

                    @Override
                    public void onFail() {
                        //Failed
                    }

                    @Override
                    public void onProgress(float percent) {
                        //Progress
                        VideoProgress = (int) percent;
                        Observables.onNext("onProgress");

                    }
                });
    }//_____________________________________________________________________________________________ End ResizeVideo


    public String GetPathFromUri(Uri uri) {//_______________________________________________________ Start GetPathFromUri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            } else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }

        return null;
    }//_____________________________________________________________________________________________ End GetPathFromUri


    private String getDataColumn(
            Context context,
            Uri uri,
            String selection,
            String[] selectionArgs) {//_____________________________________________________________ Start getDataColumn

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }//_____________________________________________________________________________________________ End getDataColumn


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

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
        } else {
            TextAddress = "";
        }

        Observables.onNext("GetAddress");

    }//_____________________________________________________________________________________________ End SetAddress


    public void DeleteFiles(int position) {//_______________________________________________________ Start DeleteFiles

        File f = new File(Files.get(position).getFileName());
        if (f.exists())
            f.delete();
        Files.remove(position);
        Observables.onNext("RemoveFile");

    }//_____________________________________________________________________________________________ End DeleteFiles


    public List<ModelChooseFiles> getFiles() {//____________________________________________________ Start getFiles
        int posission = 0;
        File fileOrDirectory = new File(Environment.getExternalStorageDirectory(),
                context.getResources().getString(R.string.FolderName));
        if (!fileOrDirectory.exists())
            Files.clear();
        else {

            int j = Files.size();
            for (int i = 0; i < j; i++) {
                File f = new File(Files.get(posission).getFileName());
                if (!f.exists())
                    Files.remove(posission);
                else
                    posission++;
            }
//            if (Files.size() == 0)
//                DeleteFileAndFolder();
        }

        return Files;
    }//_____________________________________________________________________________________________ End getFiles


    public String getTextAddress() {//______________________________________________________________ Start getTextAddress
        return TextAddress;
    }//_____________________________________________________________________________________________ End getTextAddress


    public int getVideoProgress() {//_______________________________________________________________ Start getVideoProgress
        return VideoProgress;
    }//_____________________________________________________________________________________________ End getVideoProgress


    public int FileCount() {//______________________________________________________________________ Start FileCount
        return Files.size();
    }//_____________________________________________________________________________________________ End FileCount

}
