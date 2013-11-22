package com.example.keddreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.keddreader.R;
import com.example.keddreader.fragment.TitlesFragment;

public class MyArrayAdapter extends ArrayAdapter<String> {
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

        MyArrayAdapter.ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = TitlesFragment.layoutInflater;
            rowView = inflater.inflate(R.layout.list_item, null, true);
            holder = new MyArrayAdapter.ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.list_item_title);
            holder.pubDate = (TextView) rowView.findViewById(R.id.list_item_pub_date);
            holder.author = (TextView) rowView.findViewById(R.id.list_item_author);
            rowView.setTag(holder);
        } else {
            holder = (MyArrayAdapter.ViewHolder) rowView.getTag();
        }

        holder.title.setText(titles[position]);
        holder.pubDate.setText(pubDates[position]);
        holder.author.setText(authors[position]);

        return rowView;
    }
}
