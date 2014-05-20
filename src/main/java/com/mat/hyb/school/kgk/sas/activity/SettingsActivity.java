package com.mat.hyb.school.kgk.sas.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.SystemService;

/**
 * Created by matous on 3.5.14 for iSAS.
 */
@EActivity
public class SettingsActivity extends SherlockPreferenceActivity {

    private static final String AUTHOR_KEY = "author";
    private static final String VERSION_KEY = "app_version";
    private static final String OPEN_KEY = "open";
    private static final String CLASS_KEY = "class";
    private static final String SOURCE_KEY = "source";
    private static final String LIBS_KEY = "libs";

    @Bean
    PreferenceProvider preferenceProvider;

    @SystemService
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_ab);
        getSupportActionBar().setTitle(R.string.action_settings);
        addPreferencesFromResource(R.xml.settings);

        final SettingsActivity activity = this;
        Preference changeClass = findPreference(CLASS_KEY);
        if (changeClass != null) {
            changeClass.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ClassChooserDialog dialog = new ClassChooserDialog(activity);
                    dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
                        @Override
                        public void selected(ClassID id) {
                            preferenceProvider.setDefaultClass(id);
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }

        if (preferenceProvider.isTeacher()) {
            if (changeClass != null) {
                changeClass.setEnabled(false);
            }
        }

        Preference version = findPreference(VERSION_KEY);
        if (version != null && getPackageManager() != null) {
            try {
                version.setSummary(getPackageManager()
                        .getPackageInfo(getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        Preference author = findPreference(AUTHOR_KEY);
        if (author != null) {
            author.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.google.com/+MatoušHýbl"));
                    startActivity(intent);
                    return false;
                }
            });
        }

        Preference source = findPreference(SOURCE_KEY);
        if (source != null) {
            source.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://github.com/matoushybl/iSASv2/tree/develop"));
                    startActivity(intent);
                    return false;
                }
            });
        }

        Preference libs = findPreference(LIBS_KEY);
        if (libs != null) {
            libs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(getString(R.string.libs_label));
                    builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setMessage("* Android Annotations\n* ActionBarSherlock\n* SystemBarTint");
                    builder.show();
                    return false;
                }
            });
        }
    }

    @OptionsItem(android.R.id.home)
    void back() {
        this.finish();
    }
}
