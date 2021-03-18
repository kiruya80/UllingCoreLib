package com.ulling.lib.core.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.ulling.lib.core.R;
import com.ulling.lib.core.viewutil.QcWebViewClient;

public class QcWebView extends FrameLayout {

    private Context context;

    private WebView webView;
    private RelativeLayout rlProgressBar;
    private ProgressBar progressBar;

    private WebViewTask webViewTask;
    private QcWebViewClient qcWebViewClient;
    private String url;

    public QcWebView(Context context) {
        super(context);
        initView(context);
    }

    public QcWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public QcWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public WebView getWebView() {
        return webView;
    }

    private void initView(Context context) {
        this.context = context;
        View view = inflate(getContext(), R.layout.webview_loading, null);
        addView(view);
        webView = (WebView) view.findViewById(R.id.webView);
        rlProgressBar = (RelativeLayout) view.findViewById(R.id.rl_progressBar);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        setWebview();
    }

    private void setWebview() {
        webView.clearCache(true);
        webView.clearHistory();

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                if (window.canGoBack()) {
                    window.goBack();
                }
            }
        });

        qcWebViewClient = new QcWebViewClient();
        qcWebViewClient.setListener(new QcWebViewClient.OnWebViewClientListener() {
            @Override
            public void onLodingFinish() {
                loadingProgress(false);
            }
        });
        webView.setWebViewClient(qcWebViewClient);
        setWebSettings(webView.getSettings());
    }

    private void setWebSettings(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("UTF-8");

        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //21
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }

    public void loadUrl(String url_) {
        if (url_ != null && !TextUtils.isEmpty(url_)) {
            this.url = url_;
            loadingProgress(true);
            webViewTask = new WebViewTask();
            webViewTask.execute();
        }
    }

    private class WebViewTask extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... param) {
            SystemClock.sleep(100);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            webView.loadUrl(url);
        }
    }

    private void loadingProgress(boolean isVisiable) {
        if (isVisiable) {
            rlProgressBar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            rlProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }
}