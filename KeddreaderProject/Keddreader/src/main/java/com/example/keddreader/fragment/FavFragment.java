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
import com.example.keddreader.adapter.FavsAdapter;
import com.example.keddreader.helper.FavDbHelper;
import com.example.keddreader.model.Article;

public class FavFragment extends BaseListFragment{

    private String[] titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataFromFavDb();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        Article article = App.favDbHelper.getArticleByTitle(titles[position]);
        feedSingleton.setCurrentArticle(article);

        Boolean isFav = App.favDbHelper.isFav(article);

        if(isTabletLand()){
            // Find article fragment and set it's content to current article
            ArticleFragment articleFragment = (ArticleFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            articleFragment.setSingletonCurrentContent();

            // Update menu to show a "star" icon
            getActivity().supportInvalidateOptionsMenu();

            // Unselect current item in titles fragment
            ((TitlesFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.titles_fragment)).unselectCurrent();

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

    public void setEmpty(){
        setListAdapter(null);
    }

    public void unselectCurrent(){
        getListView().clearChoices();
    }

    public void setDataFromFavDb(){
        FavDbHelper helper = App.favDbHelper;
        titles = helper.getFavTitles();
        setListAdapter(new FavsAdapter(getActivity(), helper.getFavs()));
    }

}

