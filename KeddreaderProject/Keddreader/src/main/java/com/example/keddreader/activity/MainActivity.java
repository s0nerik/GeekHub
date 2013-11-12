package com.example.keddreader.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.keddreader.R;
import com.example.keddreader.fragment.ArticleFragment;
import com.example.keddreader.fragment.TitlesFragment;
import com.example.keddreader.model.AsyncFeedGetter;
import com.example.keddreader.model.Feed;
import com.example.keddreader.model.FeedSingleton;

public class MainActivity extends ActionBarActivity implements AsyncFeedGetter{

    String[] titles;
    String[] contents;
    String current_content;
    TitlesFragment titlesFragment;
    FeedSingleton feedSingleton = FeedSingleton.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlesFragment = (TitlesFragment) getSupportFragmentManager().findFragmentById(R.id.titles_fragment);

        if(savedInstanceState == null){
//            updateRSS();
            // Load default page if running on the tablet in landscape orientation
            if (getResources().getBoolean(R.bool.isTablet) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                WebView page = (WebView) findViewById(R.id.article_WebView);
                page.loadUrl("file:///android_asset/default.html");
            }
        }else{
            titles = feedSingleton.getFeed().getTitles();
            contents = feedSingleton.getFeed().getContents();

            if (getResources().getBoolean(R.bool.isTablet)){
                current_content = feedSingleton.getFeed().getCurrentContent();
                switch(getResources().getConfiguration().orientation){
                    case Configuration.ORIENTATION_LANDSCAPE:
                        if(current_content != null){
                            ArticleFragment article = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);
                            article.setContent(current_content);
                        }
                        break;
                    case Configuration.ORIENTATION_PORTRAIT:
                        if(current_content != null){
                            Intent intent = new Intent(this, ArticleActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }else{
                titlesFragment.setTitles(titles);
                titlesFragment.setContents(contents);
            }
        }
    }

    private void updateRSS(){
        titlesFragment.setEmpty();
        feedSingleton.refreshFeed(this);
    }

    @Override
    public void onFeedParsed(Feed feed){
        titles = feedSingleton.getFeed().getTitles();
        contents = feedSingleton.getFeed().getContents();

        titlesFragment.setTitles(titles);
        titlesFragment.setContents(contents);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putStringArray("titles", titles);
        state.putStringArray("contents", contents);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateRSS();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
