package com.mat.hyb.school.kgk.sas.activity;

import com.mat.hyb.school.kgk.sas.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.res.StringRes;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@OptionsMenu(R.menu.substitution)
@EActivity(R.layout.activity_browser)
public class SubstitutionActivity extends BrowserActivity {

    @StringRes
    protected String substitution;

    @OptionsItem
    protected void today() {
        setSupportProgressBarIndeterminateVisibility(true);
        getWebView().loadUrl(getUrlProvider().getSubstitutionTodayUrl());
    }

    @OptionsItem
    protected void nextDay() {
        setSupportProgressBarIndeterminateVisibility(true);
        getWebView().loadUrl(getUrlProvider().getSubstitutionTomorrowUrl());
    }

    @Override
    protected String getShortcutType() {
        return ShortcutActivity.SUBSTITUTION;
    }

    @Override
    protected String getBaseUrl() {
        return getUrlProvider().getSuggestedDateUrl();
    }

    @Override
    protected String getActivityTitle() {
        return substitution;
    }

    @Override
    protected int getShortcutResource() {
        return R.drawable.ic_shortcut_subst;
    }
}
