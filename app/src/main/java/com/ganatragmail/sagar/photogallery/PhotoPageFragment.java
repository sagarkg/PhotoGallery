package com.ganatragmail.sagar.photogallery;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhotoPageFragment extends VisibleFragment{

    private String mUrl;
    private WebView mWebView;


    public PhotoPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mUrl = getActivity().getIntent().getData().toString();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_page, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if (NavUtils.getParentActivityName(getActivity())!= null)
                ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        final TextView titleTextView = (TextView)rootView.findViewById(R.id.titleTextView);

        mWebView = (WebView)rootView.findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webView, int progress){
                if(progress == 100){
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }

            }

            public void onReceivedTitle(WebView webview, String title){
                titleTextView.setText(title);

            }
        });

        mWebView.loadUrl(mUrl);

        return rootView;
    }



}
