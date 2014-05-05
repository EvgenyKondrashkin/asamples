package com.example.asamles.app.socialnetwork.twitterutils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asamles.app.R;
import com.example.asamles.app.socialnetwork.SocialNetworkMain;

public class TwitterDialog extends Dialog {

	private static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.FILL_PARENT);

	private Context parent;
	private FrameLayout mContent;
	private LinearLayout webViewContainer;
	private WebView mWebView;
	private ImageView mCrossImage;
	private ProgressDialog mSpinner;
	private String mUrl;

	public TwitterDialog(Context context, String url) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);

		parent = getOwnerActivity();
//			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mUrl = url;

		mSpinner = new ProgressDialog(getContext());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Loading...");

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.twitter_web_dialog);

		mContent = (FrameLayout) findViewById(R.id.twitter_web_view_content);
		
//			Display display = getWindow().getWindowManager()
//					.getDefaultDisplay();
//			int width = display.getWidth();
//			int height = display.getHeight();
//			LayoutParams lp = new LayoutParams(width * 7 / 8, height * 11 / 14);
//			mContent.setLayoutParams(lp);
			mContent.setPadding(10,10,10,10);//width / 8, height / 14, 0, 0);
		
		webViewContainer = (LinearLayout) findViewById(R.id.twitter_web_view_container);
		mCrossImage = (ImageView) findViewById(R.id.twitter_close_web_dialog);

		mCrossImage.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				TwitterDialog.this.dismiss();
				if (SocialNetworkMain.progressDialog1 != null
						&& SocialNetworkMain.progressDialog1.isShowing()) {
                    SocialNetworkMain.progressDialog1.dismiss();
				}
			}
		});
		int margin = mCrossImage.getDrawable().getIntrinsicWidth() / 2;
		mCrossImage.setVisibility(View.INVISIBLE);
		setUpWebView(margin);
	}

	@Override
	protected void onStop() {
		if (SocialNetworkMain.progressDialog1 != null && SocialNetworkMain.progressDialog1.isShowing()) {
            SocialNetworkMain.progressDialog1.dismiss();
		}
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (SocialNetworkMain.progressDialog1 != null && SocialNetworkMain.progressDialog1.isShowing()) {
            SocialNetworkMain.progressDialog1.dismiss();
		}
	}

	private void setUpWebView(int margin) {
		mWebView = new WebView(getOwnerActivity());
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new TwitterDialog.TwWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
		mWebView.setLayoutParams(FILL);

		webViewContainer.setPadding(margin, margin, margin, margin);
		webViewContainer.addView(mWebView);
	}

	private class TwWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d("Twitter-WebView", "Redirect URL: " + url);

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
			} else {
				if (SocialNetworkMain.progressDialog1 != null
						&& SocialNetworkMain.progressDialog1.isShowing()) {
                    SocialNetworkMain.progressDialog1.dismiss();
				}
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
			Log.d("Twitter-WebView", "Webview loading URL: " + url);
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
			mContent.setBackgroundColor(Color.TRANSPARENT);
			mWebView.setVisibility(View.VISIBLE);
			mCrossImage.setVisibility(View.VISIBLE);
		}
	}

}
