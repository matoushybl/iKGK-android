package com.mat.hyb.school.kgk.sas.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.analytics.HitBuilders;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.utility.ShortcutHelper;
import com.mat.hyb.school.kgk.sas.utility.UrlProvider;
import com.mat.hyb.school.kgk.sas.view.HttpAuthenticationDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@OptionsMenu(R.menu.browser)
@EActivity(R.layout.activity_browser)
public abstract class BrowserActivity extends BaseActivity {

    private static final String CATEGORY_FEATURE = "feature";

    @Bean
    protected UrlProvider urlProvider;

    @ViewById
    protected WebView webView;

    @OptionsMenuItem
    protected MenuItem refresh;

    @AfterViews
    protected void init() {
        getSupportActionBar().setElevation(0);
        setSupportProgressBarVisibility(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getActivityTitle());
        CookieManager.getInstance().setAcceptCookie(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    setSupportProgressBarVisibility(false);
                } else {
                    setSupportProgressBarVisibility(true);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setSupportProgressBarVisibility(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, @NonNull final HttpAuthHandler handler, String host, String realm) {
                String username = null;
                String password = null;

                if (handler.useHttpAuthUsernamePassword()) {
                    String[] credentials = view.getHttpAuthUsernamePassword(host, realm);
                    if (credentials != null && credentials.length == 2) {
                        username = credentials[0];
                        password = credentials[1];
                    }
                }

                if (username != null && password != null) {
                    handler.proceed(username, password);
                } else {
                    HttpAuthenticationDialog dialog
                            = new HttpAuthenticationDialog(BrowserActivity.this, host, realm);
                    dialog.setOkListener(new HttpAuthenticationDialog.OkListener() {
                        @Override
                        public void onOk(String host, String realm, String username, String password) {
                            handler.proceed(username, password);
                        }
                    });

                    dialog.setCancelListener(new HttpAuthenticationDialog.CancelListener() {
                        @Override
                        public void onCancel() {
                            handler.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= 11) {
            webView.getSettings().setDisplayZoomControls(false);
        }
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getBaseUrl());
    }

    @OptionsItem(android.R.id.home)
    protected void home() {
        this.finish();
    }

    @OptionsItem
    protected void refresh() {
        setSupportProgressBarVisibility(true);
        webView.reload();
    }

    @OptionsItem
    protected void backward() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    @OptionsItem
    protected void forward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (webView != null) {
            menu.findItem(R.id.forward).setEnabled(webView.canGoForward());
            menu.findItem(R.id.backward).setEnabled(webView.canGoBack());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setSupportProgressBarVisibility(boolean visible) {
        if (refresh != null) {
            if (visible) {
                refresh.setActionView(R.layout.refresh_layout);
                refresh.expandActionView();
            } else {
                refresh.collapseActionView();
                refresh.setActionView(null);
            }
        }
    }

    @OptionsItem
    protected void open() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webView.getUrl()));
        startActivity(intent);
    }

    @OptionsItem
    protected void shortcut() {
        getTracker().send(new HitBuilders.EventBuilder()
                .setCategory(CATEGORY_FEATURE)
                .setAction("shortcut")
                .setLabel(getActivityTitle())
                .build());
        ShortcutHelper.createShortcut(this, getActivityTitle(), getShortcutType(), getShortcutResource());
    }

    protected abstract String getShortcutType();

    protected abstract String getBaseUrl();

    protected abstract String getActivityTitle();

    protected WebView getWebView() {
        return webView;
    }

    protected UrlProvider getUrlProvider() {
        return urlProvider;
    }

    protected abstract
    @DrawableRes
    int getShortcutResource();
}
