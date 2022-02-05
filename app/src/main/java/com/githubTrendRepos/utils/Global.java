package com.githubTrendRepos.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Global {
    public static Typeface regular, bold, light;
    public static final String GET_RESPONSE = "GET_RESPONSE";
    public static final String VOLLEY_ERROR = "VOLLEY_ERROR";

    public static void initializeFont(Context context) {

        regular = Typeface.createFromAsset(context.getAssets(),
                Fonts.REGULAR);
        bold = Typeface.createFromAsset(context.getAssets(),
                Fonts.BOLD);
        light = Typeface.createFromAsset(context.getAssets(),
                Fonts.LIGHT);

    }


    public static void setCustomFont(Typeface font, View... group) {
        int count = group.length;
        View v;
        for (int i = 0; i < count; i++) {
            v = group[i];
            if (v instanceof TextView || v instanceof EditText
                    || v instanceof Button) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font);
        }
    }



    public static void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof EditText
                    || v instanceof Button) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font);
        }
    }

}
