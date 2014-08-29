package com.mat.hyb.school.kgk.sas.activity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.UrlProvider;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@OptionsMenu(R.menu.substitution)
@EActivity(R.layout.activity_browser)
public class SubstitutionActivity extends BrowserActivity {

    @Bean
    UrlProvider provider;

    @OptionsItem
    void today() {
        setSupportProgressBarIndeterminateVisibility(true);
        webView.loadUrl(provider.getSubstitutionTodayUrl());
    }

    @OptionsItem
    void nextDay() {
        setSupportProgressBarIndeterminateVisibility(true);
        webView.loadUrl(provider.getSubstitutionTomorrowUrl());
    }
}
