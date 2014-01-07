package com.lwm.app.activity;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lwm.app.R;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends ListFragment {

        Cursor cursor;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            //your database elect statement
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            //your projection statement
            String[] projection = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.ALBUM_ID
            };
            //query
            cursor = getActivity().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);

//            ArrayList<> songs;

            while(cursor.moveToNext()){
                Log.d("LWM", cursor.getString(3));
//                songs.add(cursor.getString(0));
//                songs.add(cursor.getString(1));
//                songs.add(cursor.getString(2));
//                songs.add(cursor.getString(3));
//                songs.add(cursor.getString(4));
//                songs.add(cursor.getString(5));
//                album_id.add((long) cursor.getFloat(6));
            }
//            int a[]= new int[]{R.id.textView1 ,R.id.textView3};//, R.id.textview2};
            ListAdapter adapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_2, cursor,
                    new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST},
                    new int[]{android.R.id.text1, android.R.id.text2});
            setListAdapter(adapter);

            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            cursor.moveToPosition(position);
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(cursor.getString(3));
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
            super.onListItemClick(l, v, position, id);
        }
    }

}
