package com.example.keddreader.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.keddreader.R;

public class FavsAdapter extends CursorAdapter {

    public FavsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_item, parent, false);

        return retView;
    }

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
