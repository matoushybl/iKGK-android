package com.mat.hyb.school.isas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.mat.hyb.school.isas.R;
import com.mat.hyb.school.isas.provider.ClassID;
import com.mat.hyb.school.isas.provider.PreferenceProvider;
import com.mat.hyb.school.isas.provider.UrlProvider;
import com.mat.hyb.school.isas.view.ClassChooserDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.res.StringRes;

@OptionsMenu(R.menu.main)
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

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

    @Click
    void marksClicked() {
        BrowserActivity_.intent(getApplicationContext()).title(marks)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getMarksUrl()).start();
    }

    @Click
    void timetableClicked() {
        TimetableActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .url(urlProvider.getTimetableUrl(preferenceProvider.getDefaultClass().getId()))
                .title(timetable).start();
    }

    @Click
    void canteenClicked() {
        BrowserActivity_.intent(getApplicationContext()).title(canteen)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getCanteenUrl()).start();
    }

    @Click
    void moodleClicked() {
        BrowserActivity_.intent(getApplicationContext()).title(moodle)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK).url(urlProvider.getMoodleUrl()).start();
    }

    @AfterInject
    void init() {
        if (preferenceProvider.isFirstRun()) {
            ClassChooserDialog dialog = new ClassChooserDialog(this);
            dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
                @Override
                public void selected(ClassID id) {
                    preferenceProvider.setDefaultClass(id);
                }
            });
            dialog.show();
            preferenceProvider.setFirstRun();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setStatusBarTintColor(getResources().getColor(R.color.baseColor));
            systemBarTintManager.setNavigationBarAlpha(0.0f);
            systemBarTintManager.setNavigationBarTintColor(getResources().getColor(android.R.color.black));
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setNavigationBarTintEnabled(true);
        }
        getActionBar().setIcon(R.drawable.ic_ab);
    }

}
