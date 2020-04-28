package com.andraganoid.verymuchtodo.util;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;

public class BindingAdaptersK {

    @BindingAdapter({"drawableTint"})
    public static void setDrawableTint(TextView view, @ColorInt int color) {
        for (Drawable d : view.getCompoundDrawables()
        ) {
            if (d != null) {
                d.setTint(color);
            }
        }
    }


}
