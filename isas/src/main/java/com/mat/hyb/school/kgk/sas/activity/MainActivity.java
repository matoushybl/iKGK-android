package com.mat.hyb.school.kgk.sas.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.intro.IntroActivity_;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.DownloadingHelper;
import com.mat.hyb.school.kgk.sas.utility.LunchReminderHelper;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONException;

import java.io.IOException;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Bean
    UrlProvider urlProvider;

    @Bean
    LunchReminderHelper lunchReminderHelper;

    @Bean
    DownloadingHelper downloadingHelper;

    @Pref
    ISASPrefs_ prefs;

    @AfterInject
    @Background
    void loadClasses() {
        try {
            downloadingHelper.loadClasses();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @AfterInject
    @Background
    void loadTeachers() {
        try {
            downloadingHelper.loadTeachers();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Click
    void marksClicked() {
        sendEvent(CATEGORY_PAGE, "marks");
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(UrlProvider.MARKS);
        } else {
            MarksActivity_.intent(this).start();
        }
    }

    @Click
    void timetableClicked() {
        sendEvent(CATEGORY_PAGE, "timetable");
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            TimetableActivity_.intent(this).start();
        }
    }

    @Click
    void canteenClicked() {
        sendEvent(CATEGORY_PAGE, "canteen");
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(UrlProvider.CANTEEN);
        } else {
            CanteenActivity_.intent(this).start();
        }
    }

    @Click
    void moodleClicked() {
        sendEvent(CATEGORY_PAGE, "moodle");
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(UrlProvider.MOODLE);
        } else {
            MoodleActivity_.intent(this).start();
        }
    }

    @Click
    void websiteClicked() {
        sendEvent(CATEGORY_PAGE, "website");
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(UrlProvider.WEBSITE);
        } else {
            WebsiteActivity_.intent(this).start();
        }
    }

    @Click
    void substitutionClicked() {
        sendEvent(CATEGORY_PAGE, "substitution");
        if (prefs.externalBrowserMode().get()) {
            openInBrowser(urlProvider.getSuggestedSubstitutionUrl());
        } else {
            SubstitutionActivity_.intent(this).start();
        }
    }

    @AfterViews
    void init() {
        getSupportActionBar().setElevation(0);
        if (prefs.firstRun().get()) {
            IntroActivity_.intent(this).start();
            finish();
        }
        lunchReminderHelper.enableTiming();
    }

    @OptionsItem
    void rate() {
        openInBrowser(UrlProvider.PLAY);
    }

    @OptionsItem
    void settings() {
        SettingsActivity_.intent(this).start();
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
