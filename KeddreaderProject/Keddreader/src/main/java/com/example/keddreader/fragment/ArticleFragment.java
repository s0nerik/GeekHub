package com.example.keddreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.keddreader.R;
import com.example.keddreader.model.Connectivity;

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
        if(!feedSingleton.feedAvailable() || feedSingleton.getFeed().getCurrentContent() == null){
            if(Connectivity.isConnected(getActivity())){
                page.loadUrl("file:///android_asset/default.html");
            }else{
                page.loadUrl("file:///android_asset/no_connection.html");
                new Runnable(){
                    Object waiter = new Object();
                    @Override
                    public void run(){
                        while(true){
                            try {
                                waiter.wait(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(Connectivity.isConnected(getActivity())){
                                page.loadUrl("file:///android_asset/default.html");
                                break;
                            }
                        }
                    }
                }.run();
            }
        }

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

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return true;
        }

    }

}