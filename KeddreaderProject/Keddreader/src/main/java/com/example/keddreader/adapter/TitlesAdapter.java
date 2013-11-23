package com.example.keddreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.keddreader.R;
import com.example.keddreader.model.Article;
import com.example.keddreader.model.Feed;

public class TitlesAdapter extends ArrayAdapter<Article> {
    private final Context context;
    private final String[] titles;
    private final String[] pubDates;
    private final String[] authors;

    public TitlesAdapter(Context context, Feed feed) {
        super(context, R.layout.list_item, feed.getMessages());
        this.context = context;
        this.titles = feed.getTitles();
        this.pubDates = feed.getPubDates();
        this.authors = feed.getAuthors();
    }

    static class ViewHolder {
        public TextView title;
        public TextView pubDate;
        public TextView author;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TitlesAdapter.ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.list_item, null, true);
            holder = new TitlesAdapter.ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.list_item_title);
            holder.pubDate = (TextView) rowView.findViewById(R.id.list_item_pub_date);
            holder.author = (TextView) rowView.findViewById(R.id.list_item_author);
            rowView.setTag(holder);
        } else {
            holder = (TitlesAdapter.ViewHolder) rowView.getTag();
        }

        holder.title.setText(titles[position]);
        holder.pubDate.setText(pubDates[position]);
        holder.author.setText(authors[position]);

        return rowView;
    }
}
