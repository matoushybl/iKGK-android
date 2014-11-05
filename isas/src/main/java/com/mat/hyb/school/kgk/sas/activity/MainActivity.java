package com.mat.hyb.school.kgk.sas.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.ActionBarActivity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider;
import com.mat.hyb.school.kgk.sas.provider.UrlProvider;
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

import java.util.Calendar;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @Bean
    protected UrlProvider urlProvider;

    @Bean
    protected PreferenceProvider preferenceProvider;

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
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getMarksUrl());
        } else {
            MarksActivity_.intent(getApplicationContext()).title(marks)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getMarksUrl()).start();
        }
    }

    @Click
    protected void timetableClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            TimetableActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .url(urlProvider.getOurTimetableUrl()).title(timetable).start();
        }
    }

    @Click
    protected void canteenClicked() {
        openInBrowserActivity(UrlProvider.getCanteenUrl(), canteen);
    }

    @Click
    protected void moodleClicked() {
        openInBrowserActivity(UrlProvider.getMoodleUrl(), moodle);
    }

    @Click
    protected void websiteClicked() {
        openInBrowserActivity(UrlProvider.WEBSITE, website);
    }

    @Click
    protected void substitutionClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getSuggestedDateUrl());
        } else {
            SubstitutionActivity_.intent(getApplicationContext()).title(substitution)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getSuggestedDateUrl())
                    .start();
        }
    }

    @AfterViews
    protected void init() {
        if (preferenceProvider.isFirstRun() || shouldUpdateClass()) {
            ClassChooserDialog dialog = new ClassChooserDialog(this);
            dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
                @Override
                public void selected(ClassID id) {
                    preferenceProvider.setDefaultClass(id);
                    preferenceProvider.setFirstRun();
                    preferenceProvider.setLastChange(System.currentTimeMillis());
                }
            });
            dialog.show();
        }
        getSupportActionBar().setElevation(0);
    }

    private boolean shouldUpdateClass() {
        if (preferenceProvider.getLastChange() == 0) {
            return true;
        }
        Calendar lastUpdateCalendar = Calendar.getInstance();
        lastUpdateCalendar.setTimeInMillis(preferenceProvider.getLastChange());

        Calendar currentCalendar = Calendar.getInstance();

        return (lastUpdateCalendar.get(Calendar.MONTH) < Calendar.SEPTEMBER
                || lastUpdateCalendar.get(Calendar.YEAR) < currentCalendar.get(Calendar.YEAR))
                && currentCalendar.get(Calendar.MONTH) >= Calendar.SEPTEMBER;
    }

    @OptionsItem
    protected void settings() {
        SettingsActivity_.intent(getApplicationContext())
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }

    private void openInBrowserActivity(String url, String title) {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(url);
        } else {
            BrowserActivity_.intent(getApplicationContext()).title(title)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(url).start();
        }
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
