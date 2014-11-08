package com.mat.hyb.school.kgk.sas.activity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.res.StringRes;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EActivity(R.layout.activity_browser)
public class MoodleActivity extends BrowserActivity {

    @StringRes
    protected String moodle;

    @Override
    protected String getShortcutType() {
        return ShortcutActivity.MOODLE;
    }

    @Override
    protected String getBaseUrl() {
        return UrlProvider.MOODLE;
    }

    @Override
    protected String getActivityTitle() {
        return moodle;
    }

    @Override
    protected int getShortcutResource() {
        return R.drawable.ic_shortcut_moodle;
    }
}
