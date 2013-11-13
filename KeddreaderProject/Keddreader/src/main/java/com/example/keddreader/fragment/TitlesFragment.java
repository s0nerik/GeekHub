package com.example.keddreader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.keddreader.R;
import com.example.keddreader.activity.ArticleActivity;

public class TitlesFragment extends BaseListFragment {

    private String[] titles;
    private String[] contents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            if(feedSingleton.feedAvailable()){
                titles = feedSingleton.getFeed().getTitles();
                contents = feedSingleton.getFeed().getContents();
                setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, titles));
            }
        }
        return inflater.inflate(R.layout.fragment_titles, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        feedSingleton.getFeed().setCurrentContent(contents[position]);
        feedSingleton.getFeed().setCurrentTitle(titles[position]);

        if(isTabletLand()){
            // Find article fragment and set it's content to current article
            ArticleFragment article = (ArticleFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            article.setSingletonCurrentContent();
        }else{
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            startActivity(intent);
        }

    }

    public void setEmpty(){
        setListAdapter(null);
    }

    public void setSingletoneTitles(){
        titles = feedSingleton.getFeed().getTitles();
        setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, titles));
    }

    public void setSingletoneContents(){
        contents = feedSingleton.getFeed().getContents();
    }

}