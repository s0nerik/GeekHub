package com.example.keddreader.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.keddreader.App;
import com.example.keddreader.R;
import com.example.keddreader.fragment.ArticleFragment;
import com.example.keddreader.fragment.FavFragment;
import com.example.keddreader.fragment.TitlesFragment;
import com.example.keddreader.model.Article;
import com.example.keddreader.model.AsyncFeedGetter;
import com.example.keddreader.model.Feed;

public class MainActivity extends BaseActivity implements AsyncFeedGetter{

    private String current_content;
    private static TitlesFragment titlesFragment;
    private static FavFragment favFragment;
    private static ArticleFragment article;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static MenuItem favIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlesFragment = (TitlesFragment) fragmentManager.findFragmentById(R.id.titles_fragment);
        favFragment = (FavFragment) fragmentManager.findFragmentById(R.id.fav_fragment);

        if(isTabletLand())
            article = (ArticleFragment) fragmentManager.findFragmentById(R.id.article_fragment);

        if(savedInstanceState == null){

            // Since activity started for the first time, we need to load RSS feed
            loadRSS();

            // Hide favorites fragment to show only the titles fragment
            fragmentManager.beginTransaction()
                    .hide(favFragment)
                    .commit();

        }else{ // Screen rotated
            if (isTablet()){

                // Get current content if feed is not refreshing
                if(feedSingleton.feedAvailable() && feedSingleton.getCurrentArticle() != null)
                    current_content = feedSingleton.getCurrentArticle().getContent();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // get a "star" icon from action bar
        favIcon = menu.findItem(R.id.action_save_to_fav);

        if(isTabletLand()){
            if(feedSingleton.feedAvailable() && feedSingleton.getCurrentArticle() != null){
                // Article is shown, so show a "star" icon in action bar
                favIcon.setVisible(true);

                // If article is favorite, then show a filled "star" icon
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
                return true;
            case R.id.action_save_to_fav:
                toggleFavorite(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFeedParsed(Feed feed){
        // Feed singleton is already available, so we can set titlesFragment data
        titlesFragment.setSingletonData();
    }

    private void updateRSS(){

        // Set titles fragment empty to show spinner while feed not loaded
        titlesFragment.setEmpty();

        // Actually refresh the feed
        loadRSS();
    }

    private void loadRSS(){
        // This method starts AsyncTask that loads feed and calls onFeedParsed() when feed is parsed
        feedSingleton.refreshFeed(this);
    }

    private void toggleFavorite(MenuItem item){

        Article currentArticle = feedSingleton.getCurrentArticle();
        // If article is not favorite, set it favorite, and vice versa
        if(!currentArticleIsFav()){
            item.setIcon(R.drawable.ic_action_important);
            App.favDbHelper.addFav(currentArticle);
        }else{
            item.setIcon(R.drawable.ic_action_not_important);
            App.favDbHelper.removeFav(currentArticle);
        }

        // Update favorites list
        favFragment.setDataFromFavDb();
    }

    public void onFavIconClick(View favToggle){
        // Hide titles fragment and show favorites fragment instead
        fragmentManager.beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.titles_fragment))
                .show(getSupportFragmentManager().findFragmentById(R.id.fav_fragment))
                .commit();

        View listToggle = findViewById(R.id.all_articles_toggle);

        // Set favorites selector active
        listToggle.setBackgroundResource(android.R.color.transparent);
        favToggle.setBackgroundResource(R.drawable.list_focused_keddr);
    }

    public void onListIconClick(View listToggle){
        // Hide favorites fragment and show titles fragment instead
        fragmentManager.beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.fav_fragment))
                .show(getSupportFragmentManager().findFragmentById(R.id.titles_fragment))
                .commit();

        View favToggle = findViewById(R.id.fav_articles_toggle);

        // Set titles selector active
        favToggle.setBackgroundResource(android.R.color.transparent);
        listToggle.setBackgroundResource(R.drawable.list_focused_keddr);
    }

}