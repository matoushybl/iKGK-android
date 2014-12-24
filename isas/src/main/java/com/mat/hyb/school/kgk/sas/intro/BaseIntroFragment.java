package com.mat.hyb.school.kgk.sas.intro;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.widget.ScrollView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment
public abstract class BaseIntroFragment extends Fragment {

    @ViewById
    protected ScrollView container;

    @AfterViews
    protected void initColors() {
        container.setBackgroundColor(getResources().getColor(getMainColor()));
    }

    @ColorRes
    protected abstract int getMainColor();

    @ColorRes
    protected abstract int getDarkColor();
}
