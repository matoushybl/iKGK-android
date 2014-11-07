package com.mat.hyb.school.kgk.sas.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.ActionBarActivity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.UrlProvider;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;
import com.mat.hyb.school.kgk.sas.view.MainTile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @Bean
    protected UrlProvider urlProvider;

    @Pref
    protected ISASPrefs_ prefs;

    @StringRes
    protected String marks;

    @StringRes
    protected String canteen;

    @StringRes
    protected String timetable;

    @StringRes
    protected String moodle;

    @StringRes
    protected String website;

    @StringRes
    protected String substitution;

    @ViewById(R.id.marks)
    protected MainTile marksButton;

    @Click
    protected void marksClicked() {
        openInBrowserActivity(UrlProvider.MARKS, marks);
    }

    @Click
    protected void timetableClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            TimetableActivity_.intent(this)
                    .url(urlProvider.getOurTimetableUrl())
                    .title(timetable)
                    .start();
        }
    }

    @Click
    protected void canteenClicked() {
        openInBrowserActivity(UrlProvider.CANTEEN, canteen);
    }

    @Click
    protected void moodleClicked() {
        openInBrowserActivity(UrlProvider.MOODLE, moodle);
    }

    @Click
    protected void websiteClicked() {
        openInBrowserActivity(UrlProvider.WEBSITE, website);
    }

    @Click
    protected void substitutionClicked() {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getSuggestedDateUrl());
        } else {
            SubstitutionActivity_.intent(this)
                    .title(substitution)
                    .url(urlProvider.getSuggestedDateUrl())
                    .start();
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

    private void openInBrowserActivity(String url, String title) {
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(url);
        } else {
            BrowserActivity_.intent(this).title(title).url(url).start();
        }
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
