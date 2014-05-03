package com.mat.hyb.school.sas.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.androidannotations.annotations.EBean;

/**
 * Created by matous on 5.12.13.
 */
@EBean
public class PreferenceProvider {

    private static final String CLASS = "class";
    private static final String FIRST_RUN = "first";
    private static final String OPEN = "open";
    private SharedPreferences sharedPreferences;

    public PreferenceProvider(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public ClassID getDefaultClass() {
        return ClassID.getEnumByName(sharedPreferences.getString(CLASS, "sxA"));
    }

    public void setDefaultClass(ClassID id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLASS, id.getName());
        editor.commit();
    }

    public void setFirstRun() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_RUN, false);
        editor.commit();
    }

    public boolean isOpeningInBrowserEnabled() {
        return sharedPreferences.getBoolean(OPEN, false);
    }

    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(FIRST_RUN, true);
    }
}