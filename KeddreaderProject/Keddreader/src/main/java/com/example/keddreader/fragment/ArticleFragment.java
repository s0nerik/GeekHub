package com.example.keddreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.keddreader.R;

public class ArticleFragment extends BaseFragment {

    private WebView page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        page = (WebView) getActivity().findViewById(R.id.article_WebView);

        // If feed is refreshing or the article is not selected show default info page
        if(!feedSingleton.feedAvailable() || feedSingleton.getFeed().getCurrentContent() == null)
            page.loadUrl("file:///android_asset/default.html");

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        page.saveState(outState);
    }

    public void setSingletonCurrentContent(){
        page = (WebView) getActivity().findViewById(R.id.article_WebView);
        page.setWebViewClient(new MyWebViewClient());
        page.loadDataWithBaseURL(null, feedSingleton.getFeed().getCurrentContent(), "text/html", "UTF-8", null);
    }

    public void setEmpty(){
        page.loadUrl("file:///android_asset/default.html");
    }

//    private void initWebView(WebView wv){
//        WebSettings settings = wv.getSettings();
////        settings.setJavaScriptEnabled(true);
////        settings.setBuiltInZoomControls(true);
////        settings.setSupportZoom(true);
////        settings.setLoadWithOverviewMode(true);
//    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return true;
        }

    }

}