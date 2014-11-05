package com.mat.hyb.school.kgk.sas.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider;
import com.mat.hyb.school.kgk.sas.view.ClassChooserDialog;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment
public class SettingsFragment extends PreferenceFragment {

    private static final String VERSION_KEY = "app_version";
    private static final String OPEN_KEY = "open";
    private static final String CLASS_KEY = "class";
    private static final String SOURCE_KEY = "source";
    private static final String LIBS_KEY = "libs";

    @Bean
    PreferenceProvider preferenceProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference changeClass = findPreference(CLASS_KEY);
        if (changeClass != null) {
            changeClass.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ClassChooserDialog dialog = new ClassChooserDialog(getActivity());
                    dialog.setSelectedListener(new ClassChooserDialog.ClassSelectedListener() {
                        @Override
                        public void selected(ClassID id) {
                            preferenceProvider.setDefaultClass(id);
                            preferenceProvider.setLastChange(System.currentTimeMillis());
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }

        Preference version = findPreference(VERSION_KEY);
        if (version != null && getActivity().getPackageManager() != null) {
            try {
                version.setSummary(getActivity().getPackageManager()
                        .getPackageInfo(getActivity().getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
}
