package com.example.keddreader.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.keddreader.R;
import com.example.keddreader.helper.FavDbHelper;

public class FavsAdapter extends CursorAdapter {

    private final Context context;

    public FavsAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    static class ViewHolder {
        public TextView title;
        public TextView pubDate;
        public TextView author;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rowView = inflater.inflate(R.layout.list_item, null, false);

        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) rowView.findViewById(R.id.list_item_title);
        holder.author = (TextView) rowView.findViewById(R.id.list_item_author);
        holder.pubDate = (TextView) rowView.findViewById(R.id.list_item_pub_date);
        rowView.setTag(holder);

        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.title.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(FavDbHelper.TITLE_ID))));

        holder.pubDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(FavDbHelper.DATE_ID))));

        holder.author.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(FavDbHelper.AUTHOR_ID))));
    }

}
