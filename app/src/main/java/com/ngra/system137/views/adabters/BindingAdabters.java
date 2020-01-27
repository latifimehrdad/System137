package com.ngra.system137.views.adabters;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BindingAdabters {

    @BindingAdapter("FileName")
    public static void setFileName(TextView textView, String file) {//______________________________ Start setFileName
        String filename = file.substring(file.lastIndexOf("/") + 1);
        textView.setText(filename);
    }//_____________________________________________________________________________________________ End setFileName


    @BindingAdapter("FileSize")
    public static void setFileSize(TextView textView, int size) {//_________________________________ Start setFileSize

        float msize = (float)size / 1024;
        String s = String.format("%.2f", msize);
        textView.setText(s + " MB");

    }//_____________________________________________________________________________________________ End setFileSize
}
