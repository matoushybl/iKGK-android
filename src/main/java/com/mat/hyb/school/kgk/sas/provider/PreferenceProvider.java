package com.mat.hyb.school.kgk.sas.provider;

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
    private static final String TEACHER = "teacher";
    private static final String TEACHER_ID = "teacher_id";
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

    public boolean isTeacher() {
        return sharedPreferences.getBoolean(TEACHER, false);
    }

    public void setTeacher() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TEACHER, true);
        editor.commit();
    }

    public TeacherID getTeacherId() {
        return TeacherID.getEnumByName(sharedPreferences.getString(TEACHER_ID, "Mgr. Cvrkal"));
    }

    public void setTeacherId(TeacherID id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEACHER_ID, id.getName());
        editor.commit();
    }
}