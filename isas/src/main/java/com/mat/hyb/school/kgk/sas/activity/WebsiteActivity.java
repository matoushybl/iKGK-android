package com.mat.hyb.school.kgk.sas.activity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.res.StringRes;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EActivity(R.layout.activity_browser)
public class WebsiteActivity extends BrowserActivity {

    @StringRes
    protected String website;

    @Override
    protected String getShortcutType() {
        return ShortcutActivity.WEBSITE;
    }

    @Override
    protected String getBaseUrl() {
        return UrlProvider.WEBSITE;
    }

    @Override
    protected String getActivityTitle() {
        return website;
    }

    @Override
    protected int getShortcutResource() {
        return R.drawable.ic_shortcut_website;
    }
}
