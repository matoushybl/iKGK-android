package com.mat.hyb.school.kgk.sas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider;
import com.mat.hyb.school.kgk.sas.provider.UrlProvider;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;

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
    UrlProvider urlProvider;

    @Bean
    PreferenceProvider preferenceProvider;

    @StringRes
    String marks;

    @StringRes
    String canteen;

    @StringRes
    String timetable;

    @StringRes
    String moodle;

    @StringRes
    String website;

    @StringRes
    String substitution;

    @ViewById(R.id.marks)
    Button marksButton;

    @Click
    void marksClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getMarksUrl());
        } else {
            MarksActivity_.intent(getApplicationContext()).title(marks)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getMarksUrl()).start();
        }
    }

    @Click
    void timetableClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getOurTimetableUrl());
        } else {
            TimetableActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .url(urlProvider.getOurTimetableUrl()).title(timetable).start();
        }
    }

    @Click
    void canteenClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getCanteenUrl());
        } else {
            BrowserActivity_.intent(getApplicationContext()).title(canteen)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getCanteenUrl()).start();
        }
    }

    @Click
    void moodleClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getMoodleUrl());
        } else {
            BrowserActivity_.intent(getApplicationContext()).title(moodle)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getMoodleUrl()).start();
        }
    }

    @Click
    void websiteClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(UrlProvider.WEBSITE);
        } else {
            BrowserActivity_.intent(getApplicationContext()).title(website)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(UrlProvider.WEBSITE).start();
        }
    }

    @Click
    void substitutionClicked() {
        if (preferenceProvider.isOpeningInBrowserEnabled()) {
            openInBrowser(urlProvider.getSuggestedDateUrl());
        } else {
            SubstitutionActivity_.intent(getApplicationContext()).title(substitution)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getSuggestedDateUrl())
                    .start();
        }
    }

    @AfterViews
    void init() {
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
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if (preferenceProvider.isTeacher()) {
                        marksButton.setVisibility(View.GONE);
                    }
                }
            });
            dialog.show();
        }
        if (preferenceProvider.isTeacher()) {
            marksButton.setVisibility(View.GONE);
        }
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
    void settings() {
        SettingsActivity_.intent(getApplicationContext())
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }

    public void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setIcon(R.drawable.ic_ab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (marksButton != null) {
            if (preferenceProvider.isTeacher()) {
                marksButton.setVisibility(View.GONE);
            } else {
                marksButton.setVisibility(View.VISIBLE);
            }
        }
    }
}
