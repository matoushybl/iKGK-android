package com.mat.hyb.school.isas.activity;

import com.mat.hyb.school.isas.R;
import com.mat.hyb.school.isas.provider.ClassID;
import com.mat.hyb.school.isas.provider.UrlProvider;
import com.mat.hyb.school.isas.view.ClassChooserDialog;

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
        setProgressBarIndeterminateVisibility(true);
        webView.loadUrl(provider.getOurTimetableUrl());
    }

    @OptionsItem
    void otherClass() {
        setProgressBarIndeterminateVisibility(true);
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
