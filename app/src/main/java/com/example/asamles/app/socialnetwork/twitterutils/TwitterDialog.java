package com.example.asamles.app.socialnetwork.twitterutils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.socialnetwork.twitter.ConstantValues;

public class TwitterDialog extends Dialog {

    private WebView mWebView;
    private ImageView mCrossImage;
    private ProgressDialog mSpinner;
    private String mUrl;
    private Context context;

    public TwitterDialog(Context context, String url) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mUrl = url;
        this.context = context;
        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twitter_web_dialog);

        mCrossImage = (ImageView) findViewById(R.id.twitter_close_web_dialog);
        mCrossImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TwitterDialog.this.dismiss();
            }
        });
        setUpWebView();
    }

    private void setUpWebView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("oauth_verifier=")) {
                    Toast.makeText(context, "" + Uri.parse(url), Toast.LENGTH_LONG).show();
                    context.startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mSpinner.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSpinner.dismiss();
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);


    }

    private class TwWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean isDenied = false;
            try {
                Uri uri = Uri.parse(url);
                String param = uri.getQuery();
                String name = param.split("=")[0];
                if ("denied".equals(name)) {
                    isDenied = true;
                }
            } catch (Exception e) {
            }

            TwitterDialog.this.dismiss();
            if (!isDenied) {
                getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSpinner.dismiss();
			/*
			 * Once webview is fully loaded, set the mContent background to be
			 * transparent and make visible the 'x' image.
			 */
//            mContent.setBackgroundColor(Color.TRANSPARENT);
;
        }
    }

}
