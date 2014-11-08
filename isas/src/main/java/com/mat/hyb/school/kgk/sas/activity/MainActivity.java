package com.mat.hyb.school.kgk.sas.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.ActionBarActivity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.ClassID;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.sharedpreferences.Pref;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @Bean
    protected UrlProvider urlProvider;

    @Pref
    protected ISASPrefs_ prefs;

    @Click
    protected void marksClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            MarksActivity_.intent(this).start();
        }
    }

    @Click
    protected void timetableClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            TimetableActivity_.intent(this).start();
        }
    }

    @Click
    protected void canteenClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            CanteenActivity_.intent(this).start();
        }
    }

    @Click
    protected void moodleClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            MoodleActivity_.intent(this).start();
        }
    }

    @Click
    protected void websiteClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            WebsiteActivity_.intent(this).start();
        }
    }

    @Click
    protected void substitutionClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getSuggestedDateUrl());
        } else {
            SubstitutionActivity_.intent(this).start();
        }
    }

    @AfterViews
    protected void init() {
        getSupportActionBar().setElevation(0);
        if (prefs.firstRun().get()) {
            ClassChooserDialog dialog = new ClassChooserDialog(this);
            dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
                @Override
                public void selected(ClassID id) {
                    prefs.id().put(id.getId());
                    prefs.firstRun().put(false);
                }
            });
            dialog.show();
        }
    }

    @OptionsItem
    protected void settings() {
        SettingsActivity_.intent(this).start();
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
