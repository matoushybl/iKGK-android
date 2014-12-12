package com.mat.hyb.school.kgk.sas.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mat.hyb.school.kgk.sas.BaseApplication;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public abstract class BaseActivity extends ActionBarActivity {

    public static final String CATEGORY_PAGE = "page";
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_TEACHER = "teacher";
    public static final String CATEGORY_SOURCE = "source";
    public static final String CATEGORY_FEATURE = "feature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ISASPrefs_ prefs = new ISASPrefs_(this);
        final int orange = getResources().getColor(R.color.orange);
        final int blue = getResources().getColor(R.color.blue);
        final int red = getResources().getColor(R.color.red);
        final int pink = getResources().getColor(R.color.pink);
        final int green = getResources().getColor(R.color.green);
        final int purple = getResources().getColor(R.color.purple);
        int color = prefs.colorTheme().get();
        if (color == orange) {
            setTheme(R.style.AppTheme);
            setStatusBarColor(getResources().getColor(R.color.primaryDark));
        } else if (color == blue) {
            setTheme(R.style.BlueAppTheme);
            setStatusBarColor(getResources().getColor(R.color.darkBlue));
        } else if (color == red) {
            setTheme(R.style.RedAppTheme);
            setStatusBarColor(getResources().getColor(R.color.darkRed));
        } else if (color == pink) {
            setTheme(R.style.PinkAppTheme);
            setStatusBarColor(getResources().getColor(R.color.darkPink));
        } else if (color == green) {
            setTheme(R.style.GreenAppTheme);
            setStatusBarColor(getResources().getColor(R.color.darkGreen));
        } else if (color == purple) {
            setTheme(R.style.PurpleAppTheme);
            setStatusBarColor(getResources().getColor(R.color.darkPurple));
        }
        ((BaseApplication) getApplication()).getTracker(BaseApplication.TrackerName.APP_TRACKER);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    public Tracker getTracker() {
        return ((BaseApplication) getApplication()).getTracker(BaseApplication.TrackerName.APP_TRACKER);
    }

    public void sendEvent(String category, String action) {
        getTracker().send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }
}
