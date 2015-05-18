package com.mat.hyb.school.kgk.sas.intro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.FrameLayout;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.utility.DownloadingHelper;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;

import java.io.IOException;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment(R.layout.fragment_app_info)
public class AppInfoFragment extends BaseIntroFragment {

    @SystemService
    ConnectivityManager connectivityManager;

    @Bean
    DownloadingHelper downloadingHelper;

    @ViewById
    FrameLayout loadingProgressBar;

    @Override
    protected int getMainColor() {
        return R.color.blue;
    }

    @Override
    protected int getDarkColor() {
        return R.color.darkBlue;
    }

    @AfterViews
    @Background
    void loadData() {
        if (!isNetworkAvailable()) {
            showNetworkProblemDialog();
            return;
        }
        try {
            downloadingHelper.loadClasses();
            downloadingHelper.loadTeachers();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        hideProgressBar();
    }

    @UiThread
    void showNetworkProblemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.networkDialogTitle);
        builder.setMessage(R.string.networkDialogMessage);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.networkDialogNegativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        builder.show();
    }

    @UiThread
    void hideProgressBar() {
        loadingProgressBar.setVisibility(View.GONE);
        ((IntroActivity) getActivity()).onDataLoaded();
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
