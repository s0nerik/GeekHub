package com.example.keddreader.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.keddreader.R;

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

//    static class ViewHolder {
//        public TextView title;
//        public TextView pubDate;
//        public TextView author;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_item, parent, false);

        return retView;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder holder;
//
//        View rowView = convertView;
//        if (rowView == null) {
//            LayoutInflater inflater = TitlesFragment.layoutInflater;
//            rowView = inflater.inflate(R.layout.list_item, null, true);
//            holder = new ViewHolder();
//            holder.title = (TextView) rowView.findViewById(R.id.list_item_title);
//            holder.pubDate = (TextView) rowView.findViewById(R.id.list_item_pub_date);
//            holder.author = (TextView) rowView.findViewById(R.id.list_item_author);
//            rowView.setTag(holder);
//        } else {
//            holder = (ViewHolder) rowView.getTag();
//        }
//
//        holder.title.setText(titles[position]);
//        holder.pubDate.setText(pubDates[position]);
//        holder.author.setText(authors[position]);
//
//        return rowView;
//    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views
        TextView title = (TextView) view.findViewById(R.id.list_item_title);
        title.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

        TextView date = (TextView) view.findViewById(R.id.list_item_pub_date);
        date.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));

        TextView author = (TextView) view.findViewById(R.id.list_item_author);
        author.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));
    }
}
