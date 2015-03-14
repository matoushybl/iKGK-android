package com.mat.hyb.school.kgk.sas.intro;

import android.content.Intent;
import android.net.Uri;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment(R.layout.fragment_end)
public class EndFragment extends BaseIntroFragment {

    @Click
    protected void rateButtonClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(UrlProvider.PLAY));
        startActivity(intent);
    }

    @Override
    protected int getMainColor() {
        return R.color.pink;
    }

    @Override
    protected int getDarkColor() {
        return R.color.darkPink;
    }
}
