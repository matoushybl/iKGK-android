package com.mat.hyb.school.kgk.sas.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.view.HttpAuthenticationDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@OptionsMenu(R.menu.browser)
@EActivity(R.layout.activity_browser)
public class BrowserActivity extends ActionBarActivity {

    @ViewById
    protected
    WebView webView;

    @Extra("url")
    String url;

    @Extra("title")
    String title;

    @AfterViews
    void init() {
        setSupportProgressBarIndeterminateVisibility(true);
        if (title != null) {
            setTitle(title);
        }
    }

    @SuppressLint("NewApi")
    @AfterViews
    void initBrowser() {
        final Activity activity = this;
        CookieManager.getInstance().setAcceptCookie(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    setSupportProgressBarIndeterminateVisibility(false);
                } else {
                    setSupportProgressBarIndeterminateVisibility(true);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setSupportProgressBarIndeterminateVisibility(true);
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
                            = new HttpAuthenticationDialog(activity, host, realm);
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
        webView.loadUrl(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportProgressBarVisibility(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_ab); // needs a transparent icon
    }

    @OptionsItem(android.R.id.home)
    void home() {
        this.finish();
    }

    @OptionsItem
    void refresh() {
        setSupportProgressBarIndeterminateVisibility(true);
        webView.reload();
    }

    @OptionsItem
    void backward() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    @OptionsItem
    void forward() {
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

    @OptionsItem
    void open() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webView.getUrl()));
        startActivity(intent);
    }
}
