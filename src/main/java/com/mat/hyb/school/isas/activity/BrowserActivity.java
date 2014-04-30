package com.mat.hyb.school.isas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mat.hyb.school.isas.R;

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
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    setProgressBarIndeterminateVisibility(false);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {

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
    void back() {
        this.finish();
    }

    @OptionsItem
    void refresh() {
        setProgressBarIndeterminateVisibility(true);
        webView.reload();
    }
}
