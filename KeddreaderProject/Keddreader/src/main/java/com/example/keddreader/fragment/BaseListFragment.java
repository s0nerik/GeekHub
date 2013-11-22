package com.example.keddreader.fragment;

import android.content.res.Configuration;
import android.support.v4.app.ListFragment;

import com.example.keddreader.R;
import com.example.keddreader.model.Feed;
import com.example.keddreader.model.FeedSingleton;

public class BaseListFragment extends ListFragment {

    protected static FeedSingleton feedSingleton = FeedSingleton.getInstance();

    protected boolean isTabletLand(){
        return getResources().getBoolean(R.bool.isTablet) &&
               getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
