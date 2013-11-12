package com.example.keddreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.keddreader.R;
import com.example.keddreader.model.FeedSingleton;

public class ArticleFragment extends Fragment {

    WebView page;
    String content;
    FeedSingleton feedSingleton = FeedSingleton.getInstance(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        page = (WebView) getActivity().findViewById(R.id.article_WebView);

        if(!feedSingleton.feedAvailable() || feedSingleton.getFeed().getCurrentContent() == null){
            page.loadUrl("file:///android_asset/default.html");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        page.saveState(outState);
    }

    public void setContent(String content){
        this.content = content;
        page = (WebView) getActivity().findViewById(R.id.article_WebView);
        page.setWebViewClient(new MyWebViewClient());
        page.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
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