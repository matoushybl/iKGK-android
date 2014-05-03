package com.mat.hyb.school.sas.activity;

import com.mat.hyb.school.sas.R;
import com.mat.hyb.school.sas.provider.ClassID;
import com.mat.hyb.school.sas.provider.UrlProvider;
import com.mat.hyb.school.sas.view.ClassChooserDialog;

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

}
