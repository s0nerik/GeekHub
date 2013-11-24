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
import com.example.keddreader.adapter.TitlesAdapter;
import com.example.keddreader.model.Article;
import com.example.keddreader.model.Feed;

public class TitlesFragment extends BaseListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            if(feedSingleton.feedAvailable()){
                setSingletonData();
            }
        }
        return inflater.inflate(R.layout.fragment_titles, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        Article article = feedSingleton.getFeed().getMessages().get(position);

        feedSingleton.setCurrentArticle(article);

        Boolean isFav = App.favDbHelper.isFav(article);

        if(isTabletLand()){

            // Find article fragment and set it's content to current article
            ArticleFragment articleFragment = (ArticleFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            articleFragment.setSingletonCurrentContent();

            // Unselect current item in titles fragment
            ((FavFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fav_fragment)).unselectCurrent();

            // Update menu to show a "star" icon
            getActivity().supportInvalidateOptionsMenu();

            // Set "star" icon to filled if current article is favorite and vice versa otherwise
            MainActivity.favIcon.setVisible(true);
            if(isFav)
                MainActivity.favIcon.setIcon(R.drawable.ic_action_important);
            else
                MainActivity.favIcon.setIcon(R.drawable.ic_action_not_important);

        }else{
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            startActivity(intent);
        }

    }

    public void unselectCurrent(){
        getListView().clearChoices();
    }

    public void setEmpty(){
        setListAdapter(null);
    }

    public void setSingletonData(){
        Feed feed = feedSingleton.getFeed();
        setListAdapter(new TitlesAdapter(getActivity(), feed));
    }

}