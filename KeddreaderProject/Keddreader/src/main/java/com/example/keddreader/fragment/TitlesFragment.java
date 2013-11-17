package com.example.keddreader.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.keddreader.R;
import com.example.keddreader.activity.ArticleActivity;

public class TitlesFragment extends BaseListFragment {

    private String[] titles;
    private String[] contents;
    private String[] pubDates;
    private String[] authors;
    public static LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (savedInstanceState != null){
            if(feedSingleton.feedAvailable()){
                setSingletonData();
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

    public void setSingletonData(){
        setSingletonContents();
        setSingletonPubDates();
        setSingletonAuthors();
        setSingletonTitles();
    }

    private void setSingletonTitles(){
        titles = feedSingleton.getFeed().getTitles();
        setListAdapter(new MyArrayAdapter(getActivity(), titles, pubDates, authors));
    }

    private void setSingletonContents(){
        contents = feedSingleton.getFeed().getContents();
    }

    private void setSingletonPubDates(){
        pubDates = feedSingleton.getFeed().getPubDates();
    }

    private void setSingletonAuthors(){
        authors = feedSingleton.getFeed().getAuthors();
    }

}


class MyArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] titles;
    private final String[] pubDates;
    private final String[] authors;

    public MyArrayAdapter(Context context, String[] titles, String[] pubDates, String[] authors) {
        super(context, R.layout.list_item, titles);
        this.context = context;
        this.titles = titles;
        this.pubDates = pubDates;
        this.authors = authors;
    }

    static class ViewHolder {
        public TextView title;
        public TextView pubDate;
        public TextView author;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = TitlesFragment.layoutInflater;
            rowView = inflater.inflate(R.layout.list_item, null, true);
            holder = new ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.list_item_title);
            holder.pubDate = (TextView) rowView.findViewById(R.id.list_item_pub_date);
            holder.author = (TextView) rowView.findViewById(R.id.list_item_author);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.title.setText(titles[position]);
        holder.pubDate.setText(pubDates[position]);
        holder.author.setText(authors[position]);

        return rowView;
    }
}