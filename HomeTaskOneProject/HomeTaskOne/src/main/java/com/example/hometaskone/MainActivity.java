package com.example.hometaskone;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }else{
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
