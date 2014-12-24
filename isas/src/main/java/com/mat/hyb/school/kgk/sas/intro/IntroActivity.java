package com.mat.hyb.school.kgk.sas.intro;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.activity.MainActivity_;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EActivity(R.layout.activity_intro)
public class IntroActivity extends ActionBarActivity {

    @ViewById
    protected ViewPager viewPager;

    @ViewById
    protected Button previousButton;

    @ViewById
    protected Button nextButton;

    @ViewById
    protected LinearLayout buttonBar;

    @Pref
    protected ISASPrefs_ prefs;

    private IntroAdapter adapter;

    @AfterViews
    protected void init() {
        AppInfoFragment appInfoFragment = AppInfoFragment_.builder().build();
        AppTypeFragment appTypeFragment = AppTypeFragment_.builder().build();
        ThemeFragment themeFragment = ThemeFragment_.builder().build();
        FeatureBrowserFragment featureBrowserFragment = FeatureBrowserFragment_.builder().build();
        FeatureShortcutFragment featureShortcutFragment = FeatureShortcutFragment_.builder().build();
        EndFragment endFragment = EndFragment_.builder().build();
        adapter = new IntroAdapter(getSupportFragmentManager());
        adapter.addFragment(appInfoFragment);
        adapter.addFragment(appTypeFragment);
        adapter.addFragment(themeFragment);
        adapter.addFragment(featureShortcutFragment);
        adapter.addFragment(featureBrowserFragment);
        adapter.addFragment(endFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setColors(adapter.getItem(position));
                if (position == 0) {
                    previousButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);
                    nextButton.setText(R.string.nextButtonText);
                } else if (position == adapter.getCount() - 1) {
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    nextButton.setText(R.string.finishButton);
                } else {
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    nextButton.setText(R.string.nextButtonText);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setColors(appInfoFragment);
    }

    @Click
    protected void nextButtonClicked() {
        if (viewPager.getCurrentItem() != adapter.getCount() - 1) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            prefs.firstRun().put(false);
            MainActivity_.intent(this).start();
            finish();
        }
    }

    @Click
    protected void previousButtonClicked() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setColors(BaseIntroFragment fragment) {
        int color = getResources().getColor(fragment.getDarkColor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
        buttonBar.setBackgroundColor(color);
    }
}
