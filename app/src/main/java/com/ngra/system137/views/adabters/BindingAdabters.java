package com.ngra.system137.views.adabters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.ngra.system137.R;

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


    @BindingAdapter(value = {"FileType"})
    public static void setFileType(ImageView imageView, int type) {//_______________________________ Start setFileType

        switch (type) {
            case 1:
                imageView.setImageResource(R.drawable.ic_insert_drive_file);
                imageView.setColorFilter(
                        ContextCompat.getColor(imageView.getContext(), R.color.ML_PrimaryDark),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_image);
                imageView.setColorFilter(
                        ContextCompat.getColor(imageView.getContext(), R.color.ML_PrimaryDark),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_videocam);
                imageView.setColorFilter(
                        ContextCompat.getColor(imageView.getContext(), R.color.ML_PrimaryDark),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 4:
                imageView.setImageResource(R.drawable.ic_mic);
                imageView.setColorFilter(
                        ContextCompat.getColor(imageView.getContext(), R.color.ML_PrimaryDark),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                break;
        }

    }//_____________________________________________________________________________________________ End setFileType



}
