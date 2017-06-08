package com.application.lifehack.prodela.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.application.lifehack.prodela.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWebView extends Fragment {
    Toolbar _toolbar;
    ActionBar _actionBar;
    View _view;
    WebView _webView;
    String _lantaiBangunan,_luasBangunan;
    public FragmentWebView() {
        // Required empty public constructor
    }

    public void initiate(){
        _toolbar = (Toolbar) _view.findViewById(R.id.my_toolbar);
        _toolbar.setTitleTextColor(Color.parseColor("#fff9c4"));
        _toolbar.setSubtitleTextColor(Color.parseColor("#fff9c4"));

        ((AppCompatActivity)getActivity()).setSupportActionBar(_toolbar);
        _actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        _actionBar.setTitle("Cost Estimation");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        setHasOptionsMenu(true);

        _lantaiBangunan = getArguments().getString(FragmentHitung.LANTAI_BANGUNAN);
        _luasBangunan   = getArguments().getString(FragmentHitung.LUAS_BANGUNGAN);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_web_view, container, false);
        initiate();
        WebView wv = (WebView) _view.findViewById(R.id.webView1);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        //wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.loadUrl(getString(R.string.BASE_URL)+"kalkulasi/"+_lantaiBangunan+"/"+_luasBangunan);
        wv.setVisibility(View.INVISIBLE);
        ProgressBar pg = (ProgressBar) _view.findViewById(R.id.progressBarWebView);
        wv.setWebViewClient(new WebViewClass(wv, pg));
        return _view;
    }
    class WebViewClass extends WebViewClient {
        final ProgressBar loadProgress;
        final  WebView wv;

        WebViewClass(WebView webView, ProgressBar progressBar) {
            this.wv = webView;
            this.loadProgress = progressBar;
        }

        public void onPageFinished(WebView view, String url) {
            wv.setVisibility(View.VISIBLE);
            loadProgress.setVisibility(View.INVISIBLE);
            ((ViewGroup) loadProgress.getParent()).removeView(loadProgress);
            view.clearCache(true);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            wv.loadData("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<center>" + "error"+ ".</center>", "text/html", "UTF-8");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
                break;
        }
        return true;
    }
}
