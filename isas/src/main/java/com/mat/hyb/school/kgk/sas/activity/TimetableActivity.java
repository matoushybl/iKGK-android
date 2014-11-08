package com.mat.hyb.school.kgk.sas.activity;

import android.view.Menu;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.ClassID;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@OptionsMenu(R.menu.timetable)
@EActivity(R.layout.activity_browser)
public class TimetableActivity extends BrowserActivity {

    @StringRes
    protected String timetable;

    @Pref
    protected ISASPrefs_ prefs;

    @OptionsItem
    protected void myClass() {
        setSupportProgressBarIndeterminateVisibility(true);
        getWebView().loadUrl(getUrlProvider().getOurTimetableUrl());
    }

    @OptionsItem
    protected void otherClass() {
        setSupportProgressBarIndeterminateVisibility(true);
        ClassChooserDialog dialog = new ClassChooserDialog(this);
        dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
            @Override
            public void selected(ClassID id) {
                getWebView().loadUrl(getUrlProvider().getTimetableUrl(id.getId()));
            }
        });
        dialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (prefs.teacherMode().get()) {
            menu.findItem(R.id.myClass).setVisible(false);
            menu.findItem(R.id.otherClass).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected String getShortcutType() {
        return ShortcutActivity.TIMETABLE;
    }

    @Override
    protected String getBaseUrl() {
        return getUrlProvider().getOurTimetableUrl();
    }

    @Override
    protected String getActivityTitle() {
        return timetable;
    }

    @Override
    protected int getShortcutResource() {
        return R.drawable.ic_shortcut_timetable;
    }
}
