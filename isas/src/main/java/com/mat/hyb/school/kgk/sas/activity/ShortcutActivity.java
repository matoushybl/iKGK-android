package com.mat.hyb.school.kgk.sas.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;

import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EActivity
public class ShortcutActivity extends Activity {

    public static final String MOODLE = "moodle";
    public static final String MARKS = "marks";
    public static final String CANTEEN = "canteen";
    public static final String TIMETABLE = "timetable";
    public static final String SUBSTITUTION = "subst";
    public static final String WEBSITE = "web";

    @Extra
    protected String type;

    @Bean
    protected UrlProvider provider;

    @Pref
    protected ISASPrefs_ prefs;

    @AfterInject
    protected void startApp() {
        if (prefs.browserShortcut().get()) {
            String url;
            if (type.equals(MARKS)) {
                url = UrlProvider.MARKS;
            } else if (type.equals(MOODLE)) {
                url = UrlProvider.MOODLE;
            } else if (type.equals(WEBSITE)) {
                url = UrlProvider.WEBSITE;
            } else if (type.equals(CANTEEN)) {
                url = UrlProvider.CANTEEN;
            } else if (type.equals(TIMETABLE)) {
                url = provider.getOurTimetableUrl();
            } else {
                url = provider.getSuggestedSubstitutionUrl();
            }
            openInBrowser(url);
            finish();
        } else {
            if (type.equals(MARKS)) {
                MarksActivity_.intent(this).start();
            } else if (type.equals(MOODLE)) {
                MoodleActivity_.intent(this).start();
            } else if (type.equals(WEBSITE)) {
                WebsiteActivity_.intent(this).start();
            } else if (type.equals(CANTEEN)) {
                CanteenActivity_.intent(this).start();
            } else if (type.equals(TIMETABLE)) {
                TimetableActivity_.intent(this).start();
            } else {
                SubstitutionActivity_.intent(this).start();
            }
        }
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}

