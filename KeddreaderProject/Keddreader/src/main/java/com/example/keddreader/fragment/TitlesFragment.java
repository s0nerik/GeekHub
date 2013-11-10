package com.example.keddreader.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.keddreader.R;
import com.example.keddreader.activity.ArticleActivity;
import com.example.keddreader.model.FeedSingleton;

public class TitlesFragment extends ListFragment {

    String[] titles;
    String[] contents;
    String current_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            titles = savedInstanceState.getStringArray("titles");
            contents = savedInstanceState.getStringArray("contents");
            setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, titles));
        }
        return inflater.inflate(R.layout.fragment_titles, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putStringArray("titles", titles);
        state.putStringArray("contents", contents);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        FeedSingleton.getInstance(getActivity()).getFeed().setCurrentContent(contents[position]);
        FeedSingleton.getInstance(getActivity()).getFeed().setCurrentTitle(titles[position]);
        if(getResources().getBoolean(R.bool.isTablet) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            ArticleFragment article = (ArticleFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            article.setContent(contents[position]);
        }else{
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            startActivity(intent);
        }
        current_title = titles[position];
    }

    public void setTitles(String[] titles){
        this.titles = titles;
        setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, titles));
    }

    public void setContents(String[] contents){
        this.contents = contents;
    }

}