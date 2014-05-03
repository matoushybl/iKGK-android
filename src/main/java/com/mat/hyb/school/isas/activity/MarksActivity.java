package com.mat.hyb.school.isas.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.mat.hyb.school.isas.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matous on 3.5.14 for iSAS.
 */
@OptionsMenu(R.menu.browser)
@EActivity(R.layout.activity_browser)
public class MarksActivity extends SherlockActivity {

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
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        if (Build.VERSION.SDK_INT >= 19) {
            android.view.Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setStatusBarTintColor(getResources().getColor(R.color.baseColor));
            systemBarTintManager.setNavigationBarAlpha(0.0f);
            systemBarTintManager.setNavigationBarTintColor(getResources().getColor(android.R.color.black));
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setNavigationBarTintEnabled(true);
        }
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
    public boolean onPrepareOptionsMenu(com.actionbarsherlock.view.Menu menu) {
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
