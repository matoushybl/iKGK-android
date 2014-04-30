package com.mat.hyb.school.isas.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mat.hyb.school.isas.R;
import com.mat.hyb.school.isas.view.HttpAuthenticationDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

/**
 * Created by matous on 30.4.14 for iSAS.
 */
@OptionsMenu(R.menu.browser)
@WindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
@EActivity(R.layout.activity_browser)
public class BrowserActivity extends Activity {

    @ViewById
    WebView webView;

    @Extra("url")
    String url;

    @Extra("title")
    String title;

    @AfterViews
    void init() {
        setProgressBarIndeterminateVisibility(true);
        if (title != null) {
            setTitle(title);
        }
    }

    @AfterViews
    void initBrowser() {
        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    setProgressBarIndeterminateVisibility(false);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, String host, String realm) {
                Log.e("host", host);
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
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressBarVisibility(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_ab); // needs a transparent icon
    }

    @OptionsItem(android.R.id.home)
    void home() {
        this.finish();
    }

    @OptionsItem
    void refresh() {
        setProgressBarIndeterminateVisibility(true);
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
        return true;
    }

    @OptionsItem
    void open() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webView.getUrl()));
        startActivity(intent);
    }
}
