package com.example.keddreader.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.keddreader.R;
import com.example.keddreader.fragment.ArticleFragment;
import com.example.keddreader.fragment.TitlesFragment;
import com.example.keddreader.model.AsyncFeedGetter;
import com.example.keddreader.model.Feed;

public class MainActivity extends BaseActivity implements AsyncFeedGetter{

    private String current_content;
    private static TitlesFragment titlesFragment;
    private static ArticleFragment article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlesFragment = (TitlesFragment) getSupportFragmentManager().findFragmentById(R.id.titles_fragment);

        if(isTabletLand())
            article = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if(savedInstanceState == null){

            // Since activity started for the first time, we need to load RSS feed
            loadRSS();

            // Load default page if running on the tablet in landscape orientation
            if (isTabletLand()){
                WebView page = (WebView) findViewById(R.id.article_WebView);
                page.loadUrl("file:///android_asset/default.html");
            }

        }else{ // Screen rotated
            if (isTablet()){

                // Get current content if feed is not refreshing
                if(feedSingleton.feedAvailable())
                    current_content = feedSingleton.getFeed().getCurrentContent();

                switch(getResources().getConfiguration().orientation){

                    case Configuration.ORIENTATION_LANDSCAPE:

                        // Tablet is rotated to landscape orientation, so set current article content
                        // If content is null, default content will be shown
                        if(current_content != null)
                            article.setSingletonCurrentContent();
                        break;

                    case Configuration.ORIENTATION_PORTRAIT:

                        // Tablet rotated into portrait orientation, so start ArticleActivity
                        // If current content is null, we don't need to show ArticleActivity
                        if(current_content != null){
                            Intent intent = new Intent(this, ArticleActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }else{

                // Running on the phone, so we need only to update list
                if(feedSingleton.feedAvailable()){

                    // Feed is not refreshing, so we can set titles and contents
                    titlesFragment.setSingletoneTitles();
                    titlesFragment.setSingletoneContents();
                }
            }
        }
    }

    private void updateRSS(){

        titlesFragment.setEmpty();

        if(isTabletLand()) // Tablet is in landscape mode, so we need to clear the article view
            article.setEmpty();

        feedSingleton.refreshFeed(this);
    }

    private void loadRSS(){
        feedSingleton.refreshFeed(this);
    }

    @Override
    public void onFeedParsed(Feed feed){
        // Feed singleton is already available, so we can set titles and contents
        titlesFragment.setSingletoneTitles();
        titlesFragment.setSingletoneContents();
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
            case R.id.action_settings:
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                updateRSS();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
