package com.mat.hyb.school.isas.activity;

import com.mat.hyb.school.isas.R;
import com.mat.hyb.school.isas.provider.UrlProvider;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

/**
 * Created by matous on 2.5.14 for iSAS.
 */
@OptionsMenu(R.menu.substitution)
@EActivity(R.layout.activity_browser)
public class SubstitutionActivity extends BrowserActivity {

    @Bean
    UrlProvider provider;

    @OptionsItem
    void today() {
        setProgressBarIndeterminateVisibility(true);
        webView.loadUrl(provider.getSubstitutionTodayUrl());
    }

    @OptionsItem
    void nextDay() {
        setProgressBarIndeterminateVisibility(true);
        webView.loadUrl(provider.getSubstitutionTomorrowUrl());
    }
}
