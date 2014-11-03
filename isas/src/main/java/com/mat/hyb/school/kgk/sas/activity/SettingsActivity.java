package com.mat.hyb.school.kgk.sas.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.settings.SettingsFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EActivity
public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_ab);
        getSupportActionBar().setTitle(R.string.action_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment_()).commit();
    }

    @OptionsItem(android.R.id.home)
    void back() {
        this.finish();
    }
}
