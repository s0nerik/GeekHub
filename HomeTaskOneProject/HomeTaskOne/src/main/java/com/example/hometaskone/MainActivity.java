package com.example.hometaskone;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            ListView drawerList = (ListView) findViewById(R.id.left_drawer);
            drawerList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_activated_1, new String[]{"Post-Hardcore", "Metalcore", "Alternative Rock"}));

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            BandSelectorFragment bandSelector = new BandSelectorFragment();
            AlbumSelectorFragment albumSelector = new AlbumSelectorFragment();

            fragmentTransaction.replace(R.id.container1, bandSelector);
            fragmentTransaction.replace(R.id.container2, albumSelector);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
