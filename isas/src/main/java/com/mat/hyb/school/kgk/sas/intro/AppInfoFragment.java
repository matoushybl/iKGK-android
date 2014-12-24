package com.mat.hyb.school.kgk.sas.intro;

import com.mat.hyb.school.kgk.sas.R;
import org.androidannotations.annotations.EFragment;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment(R.layout.fragment_app_info)
public class AppInfoFragment extends BaseIntroFragment {

    @Override
    protected int getMainColor() {
        return R.color.blue;
    }

    @Override
    protected int getDarkColor() {
        return R.color.darkBlue;
    }
}
