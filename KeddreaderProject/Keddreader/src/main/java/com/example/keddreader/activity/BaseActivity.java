package com.example.keddreader.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.example.keddreader.App;
import com.example.keddreader.R;
import com.example.keddreader.model.Article;
import com.example.keddreader.model.FeedSingleton;

public class BaseActivity extends ActionBarActivity {

    protected static FeedSingleton feedSingleton = FeedSingleton.getInstance();


    protected boolean isTabletLand(){
        return getResources().getBoolean(R.bool.isTablet) &&
               getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    protected boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    protected boolean currentArticleIsFav(){
        return feedSingleton.getCurrentArticle() != null && App.favDbHelper.isFav(feedSingleton.getCurrentArticle());
    }

    protected void toggleFavorite(MenuItem item){

        Article currentArticle = feedSingleton.getCurrentArticle();
        // If article is not favorite, set it favorite, and vice versa
        if(!currentArticleIsFav()){
            item.setIcon(R.drawable.ic_action_important);
            App.favDbHelper.addFav(currentArticle);
        }else{
            item.setIcon(R.drawable.ic_action_not_important);
            App.favDbHelper.removeFav(currentArticle);
        }

    }

    protected void shareCurrentArticle(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        Article current = feedSingleton.getCurrentArticle();
        sendIntent.putExtra(Intent.EXTRA_TEXT, current.getTitle()+"\n"+current.getLink());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share article with others"));
    }

}
