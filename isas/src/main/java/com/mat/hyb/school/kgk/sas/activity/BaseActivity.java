package com.mat.hyb.school.kgk.sas.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mat.hyb.school.kgk.sas.BaseApplication;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public abstract class BaseActivity extends ActionBarActivity {

    public static final String CATEGORY_PAGE = "page";
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_TEACHER = "teacher";
    public static final String CATEGORY_SOURCE = "source";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).getTracker(BaseApplication.TrackerName.APP_TRACKER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    protected Tracker getTracker() {
        return ((BaseApplication) getApplication()).getTracker(BaseApplication.TrackerName.APP_TRACKER);
    }

    public void sendEvent(String category, String action) {
        getTracker().send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }
}
