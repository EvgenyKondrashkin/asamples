package com.example.asamles.app.socialnetwork.twitter;

import android.support.v4.app.Fragment;


public class OAuthWebViewFragment extends Fragment {
//    private WebView webView;
//    private String authenticationUrl;
//
//    public OAuthWebViewFragment(String authenticationUrl) {
//        this.authenticationUrl = authenticationUrl;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        webView.loadUrl(authenticationUrl);
//        webView.setWebViewClient(new WebViewClient()
//        {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.contains("oauth_verifier="))
//                {
////                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
////                    intent.setData( Uri.parse(url));
////                    startActivity(intent);
//                }
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        WebSettings webSettings= webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.oauth_webview,container,false);
//        webView = (WebView)view.findViewById(R.id.webViewOAuth);
//        return view;
//    }
}
