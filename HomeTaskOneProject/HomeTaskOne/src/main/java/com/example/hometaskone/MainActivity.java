package com.example.hometaskone;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer,
                R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ListView drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, new String[]{"Albums preview", "Animations"}));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch(position) {
                    case 0:
                        if(fragmentManager.findFragmentByTag("animation_selector") != null){
                            fragmentManager.popBackStack();
                            getSupportActionBar().setTitle("Albums Preview");
                        }
                        break;
                    case 1:
                        if(fragmentManager.findFragmentByTag("animation_selector") == null){
                            AnimationSelectorFragment animationSelector = new AnimationSelectorFragment();
                            AnimationFragment animation = new AnimationFragment();
                            fragmentTransaction.replace(R.id.container1, animationSelector, "animation_selector");
                            fragmentTransaction.replace(R.id.container2, animation, "animation");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            getSupportActionBar().setTitle("Animations");
                        }
                        break;
                }
            }
        });
        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            BandSelectorFragment bandSelector = new BandSelectorFragment();
            AlbumSelectorFragment albumSelector = new AlbumSelectorFragment();

            fragmentTransaction.replace(R.id.container1, bandSelector, "band_selector");
            fragmentTransaction.replace(R.id.container2, albumSelector, "album_selector");
            fragmentTransaction.addToBackStack("selection_screen");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().findFragmentByTag("album_viewer") != null){
            getSupportFragmentManager().popBackStack("selection_screen", 0);
        }else if (getSupportFragmentManager().findFragmentByTag("animation_selector") != null){
            super.onBackPressed();
            getSupportActionBar().setTitle("Albums Preview");
        }else{
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
