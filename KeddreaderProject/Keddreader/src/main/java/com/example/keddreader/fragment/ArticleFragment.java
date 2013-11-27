package com.example.keddreader.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.keddreader.R;
import com.example.keddreader.activity.ImageViewerActivity;
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
        WebSettings settings = page.getSettings();
        if ( !Connectivity.isConnected(getActivity()) ) { // loading offline
            settings.setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }else{ // Loading online
            settings.setCacheMode( WebSettings.LOAD_DEFAULT );
        }

        // If feed is refreshing or the article is not selected show default info page
        if((!feedSingleton.feedAvailable() || feedSingleton.getCurrentArticle() == null) && !currentArticleIsFav()){
            page.loadUrl("file:///android_asset/default.html");
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
        page.loadDataWithBaseURL(null, feedSingleton.getCurrentArticle().getContent(), "text/html", "UTF-8", null);
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            String type = url.substring(url.lastIndexOf("."));
//            Log.d("KEDD", "Link type is: " + type);
            if(    ".jpg".equals(type)
                || ".jpeg".equals(type)
                || ".png".equals(type)){

                Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
                view.getContext().startActivity(intent);
            }
            return true;
        }

//        @Override
//        public void onLoadResource(WebView view, String url){
//            Log.d("KEDD", "Image at "+url+" is loading...");
//        }

    }

}