package com.example.keddreader.fragment;

import android.support.v4.app.Fragment;

import com.example.keddreader.App;
import com.example.keddreader.model.FeedSingleton;

public class BaseFragment extends Fragment {

    protected static FeedSingleton feedSingleton = FeedSingleton.getInstance();

    protected boolean currentArticleIsFav(){
        return feedSingleton.getCurrentArticle() != null && App.favDbHelper.isFav(feedSingleton.getCurrentArticle());
    }

}
