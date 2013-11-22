package com.example.keddreader.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.keddreader.App;
import com.example.keddreader.R;
import com.example.keddreader.fragment.ArticleFragment;
import com.example.keddreader.fragment.FavFragment;
import com.example.keddreader.fragment.TitlesFragment;
import com.example.keddreader.helper.FavDbHelper;
import com.example.keddreader.model.AsyncFeedGetter;
import com.example.keddreader.model.Feed;
import com.example.keddreader.model.FeedSingleton;
import com.example.keddreader.service.FeedCheckerService;

public class MainActivity extends BaseActivity implements AsyncFeedGetter{

    private String current_content;
    private static TitlesFragment titlesFragment;
    private static ArticleFragment article;
    public static MenuItem favIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlesFragment = (TitlesFragment) getSupportFragmentManager().findFragmentById(R.id.titles_fragment);

        if(isTabletLand()){
            article = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);

            getSupportFragmentManager().beginTransaction()
                    .hide(getSupportFragmentManager().findFragmentById(R.id.fav_fragment))
    //                .replace(R.id.titles_fragment, new TitlesFragment())
                    .commit();
        }

        if(savedInstanceState == null){

            // Since activity started for the first time, we need to load RSS feed
            loadRSS();

        }else{ // Screen rotated
            if (isTablet()){

                // Get current content if feed is not refreshing
                if(feedSingleton.feedAvailable())
                    current_content = feedSingleton.getFeed().getCurrentContent();

                switch(getResources().getConfiguration().orientation){

                    case Configuration.ORIENTATION_LANDSCAPE:

                        // Tablet is rotated to landscape orientation, so set current article content
                        // If content is null, default content will be shown
                        if(current_content != null){
                            article.setSingletonCurrentContent();
                            supportInvalidateOptionsMenu();
                        }
                        break;

                    case Configuration.ORIENTATION_PORTRAIT:

                        // Tablet rotated into portrait orientation, so start ArticleActivity
                        // If current content is null, we don't need to show ArticleActivity
                        if(current_content != null){
                            Intent intent = new Intent(this, ArticleActivity.class);
//                            intent.putExtra("is_fav", currentArticleIsFav());
                            startActivity(intent);
                        }
                        break;
                }
            }else{

                // Running on the phone, so we need only to update list
                if(feedSingleton.feedAvailable()){

                    // Feed is not refreshing, so we can set titlesFragment data
                    titlesFragment.setSingletonData();
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
        // Feed singleton is already available, so we can set titlesFragment data
        titlesFragment.setSingletonData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        favIcon = menu.findItem(R.id.action_save_to_fav);
        if(isTabletLand()){
            if(feedSingleton.feedAvailable() && feedSingleton.getFeed().getCurrentTitle() != null){
                favIcon.setVisible(true);
                if(currentArticleIsFav()){
                    favIcon.setIcon(R.drawable.ic_action_important);
                }
            }
        }
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
                supportInvalidateOptionsMenu();
                return true;
            case R.id.action_save_to_fav:
                addFavorite(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addFavorite(MenuItem item){

        FavFragment favFragment = (FavFragment) getSupportFragmentManager().findFragmentById(R.id.fav_fragment);
        if(!currentArticleIsFav()){
            item.setIcon(R.drawable.ic_action_important);
            App.favDbHelper.addFav(feedSingleton.getFeed().getCurrentTitle(),
                                   feedSingleton.getFeed().getCurrentContent(),
                                   feedSingleton.getFeed().getCurrentAuthor(),
                                   feedSingleton.getFeed().getCurrentPubDate() );
        }else{
            item.setIcon(R.drawable.ic_action_not_important);
            App.favDbHelper.removeFav(feedSingleton.getFeed().getCurrentTitle());
        }
        favFragment.setFavDbData();
        App.favDbHelper.printCurrState();
    }

    public void onFavIconClick(View favIcon){
        getSupportFragmentManager().beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.titles_fragment))
                .show(getSupportFragmentManager().findFragmentById(R.id.fav_fragment))
//                .replace(R.id.titles_fragment, new FavFragment())
                .commit();
//        titlesFragment.setFavDbData();

        View listIcon = findViewById(R.id.all_articles_toggle);
        listIcon.setBackgroundResource(android.R.color.transparent);
//        listIcon.setBackgroundResource(R.drawable.menu_dropdown_panel_keddr);
        favIcon.setBackgroundResource(R.drawable.list_focused_keddr);
    }

    public void onListIconClick(View listIcon){
        getSupportFragmentManager().beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.fav_fragment))
                .show(getSupportFragmentManager().findFragmentById(R.id.titles_fragment))
//                .replace(R.id.titles_fragment, new TitlesFragment())
                .commit();
//        titlesFragment.setSingletonData();

        View favIcon = findViewById(R.id.fav_articles_toggle);
        favIcon.setBackgroundResource(android.R.color.transparent);
//        favIcon.setBackgroundResource(R.drawable.menu_dropdown_panel_keddr);
        listIcon.setBackgroundResource(R.drawable.list_focused_keddr);
    }

}