package com.mat.hyb.school.kgk.sas.settings;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface ISASPrefs {

    boolean teacherMode();

    int id();

    boolean externalBrowserMode();

    @DefaultBoolean(true)
    boolean firstRun();
}
