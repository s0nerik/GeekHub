package com.example.hometaskone.activity;

import android.os.Bundle;

import com.example.hometaskone.R;
import com.example.hometaskone.fragment.AlbumSelectorFragment;

public class AlbumSelectorActivity extends BasicActivity {
    private String band;
    private String[] albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            band = getIntent().getStringExtra("band");
            albums = getIntent().getStringArrayExtra("albums");
            AlbumSelectorFragment albumSelector = new AlbumSelectorFragment(band, albums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.albums_container, albumSelector)
                    .commit();
        }
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_albums;
    }

}
