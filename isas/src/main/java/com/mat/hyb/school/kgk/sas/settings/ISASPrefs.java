package com.mat.hyb.school.kgk.sas.settings;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface ISASPrefs {

    boolean teacherMode();

    @Deprecated
    int id();

    /**
     * Used in the new version with network downloading of classes
     *
     * @return id of the torch object
     */
    @DefaultLong(69)
    long torchId();

    boolean externalBrowserMode();

    @DefaultBoolean(true)
    boolean firstRun();

    boolean browserShortcut();

    @DefaultInt(-26624)
    int colorTheme();

    @DefaultBoolean(true)
    boolean lunchReminder();
}
