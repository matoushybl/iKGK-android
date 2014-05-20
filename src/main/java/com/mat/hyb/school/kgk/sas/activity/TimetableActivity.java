package com.mat.hyb.school.kgk.sas.activity;

import com.actionbarsherlock.view.Menu;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider;
import com.mat.hyb.school.kgk.sas.provider.UrlProvider;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

/**
 * Created by matous on 2.5.14 for iSAS.
 */
@OptionsMenu(R.menu.timetable)
@EActivity(R.layout.activity_browser)
public class TimetableActivity extends BrowserActivity {

    @Bean
    UrlProvider provider;

    @Bean
    PreferenceProvider preferenceProvider;

    @OptionsItem
    void myClass() {
        setSupportProgressBarIndeterminateVisibility(true);
        webView.loadUrl(provider.getOurTimetableUrl());
    }

    @OptionsItem
    void otherClass() {
        setSupportProgressBarIndeterminateVisibility(true);
        ClassChooserDialog dialog = new ClassChooserDialog(this);
        dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
            @Override
            public void selected(ClassID id) {
                webView.loadUrl(provider.getTimetableUrl(id.getId()));
            }
        });
        dialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (preferenceProvider.isTeacher()) {
            menu.findItem(R.id.myClass).setVisible(false);
            menu.findItem(R.id.otherClass).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
