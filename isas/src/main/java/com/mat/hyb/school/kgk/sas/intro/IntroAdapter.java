package com.mat.hyb.school.kgk.sas.intro;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public class IntroAdapter extends FragmentPagerAdapter {

    private List<BaseIntroFragment> fragments = new ArrayList<>();

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public BaseIntroFragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment(BaseIntroFragment fragment) {
        fragments.add(fragment);
    }
}
