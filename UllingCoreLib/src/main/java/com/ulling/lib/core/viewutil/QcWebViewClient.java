package com.ulling.lib.core.viewutil;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 */
public class QcWebViewClient extends WebViewClient {

    private OnWebViewClientListener listener;
    private String url;

    public void setListener(OnWebViewClientListener listener) {
        this.listener = listener;
    }

    public interface OnWebViewClientListener {

        void onLodingFinish();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.url = url;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (view.canGoBack()) {
            view.loadUrl(url);
            return true;
        }
        return false;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.isRedirect()) {
            view.loadUrl(url);
            return true;
        }
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (listener != null) {
            listener.onLodingFinish();
        }
        super.onPageFinished(view, url);
    }
}
