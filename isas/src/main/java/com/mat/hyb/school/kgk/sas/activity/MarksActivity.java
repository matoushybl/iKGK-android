package com.mat.hyb.school.kgk.sas.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mat.hyb.school.kgk.sas.R;

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
public class MarksActivity extends ActionBarActivity {

    @ViewById
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
        CookieManager.getInstance().setAcceptCookie(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    setSupportProgressBarIndeterminateVisibility(false);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                supportInvalidateOptionsMenu();
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= 11) {
            webView.getSettings().setDisplayZoomControls(false);
        }
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
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
