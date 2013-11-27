package com.example.keddreader.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;

import com.example.keddreader.App;
import com.example.keddreader.R;
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

}
