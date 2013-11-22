package com.example.keddreader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.keddreader.App;
import com.example.keddreader.R;
import com.example.keddreader.activity.ArticleActivity;
import com.example.keddreader.activity.MainActivity;
import com.example.keddreader.adapter.MyCursorAdapter;
import com.example.keddreader.helper.FavDbHelper;

public class FavFragment extends BaseListFragment{

    private String[] titles;
    private String[] contents;
    private String[] pubDates;
    private String[] authors;
    public static LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutInflater = inflater;
//        if (savedInstanceState != null){
            setFavDbData();
//        }
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        feedSingleton.getFeed().setCurrentContent(contents[position]);
        feedSingleton.getFeed().setCurrentTitle(titles[position]);
        feedSingleton.getFeed().setCurrentAuthor(authors[position]);
        feedSingleton.getFeed().setCurrentPubDate(pubDates[position]);

        Boolean isFav = App.favDbHelper.isFav(titles[position]);

        if(isTabletLand()){
            // Find article fragment and set it's content to current article
            ArticleFragment article = (ArticleFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            article.setSingletonCurrentContent();

            getActivity().supportInvalidateOptionsMenu();
            MainActivity.favIcon.setVisible(true);
            if(isFav){
                MainActivity.favIcon.setIcon(R.drawable.ic_action_important);
            }else{
                MainActivity.favIcon.setIcon(R.drawable.ic_action_not_important);
            }

        }else{
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            startActivity(intent);
        }

    }

    public void setEmpty(){
        setListAdapter(null);
    }

//    private void setFavDbTitles(){
//
//    }

    public void setFavDbData(){
        contents = App.favDbHelper.getFavContents();
        titles = App.favDbHelper.getFavTitles();
        authors = App.favDbHelper.getFavAuthors();
        pubDates = App.favDbHelper.getFavPubDates();
        setListAdapter(new MyCursorAdapter(getActivity(), App.readableFavDB.query(FavDbHelper.TABLE_NAME, null, null, null, null, null, null)));
    }

}

