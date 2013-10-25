package com.example.hometaskone.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.example.hometaskone.fragment.BandSelectorFragment;
import com.example.hometaskone.R;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            if (isTablet()){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                BandSelectorFragment bandSelector = new BandSelectorFragment();
                fragmentTransaction.replace(R.id.container1, bandSelector, "band_selector");
                fragmentTransaction.commit();
            }else{
                getSupportActionBar().setTitle("Band chooser");
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

}
