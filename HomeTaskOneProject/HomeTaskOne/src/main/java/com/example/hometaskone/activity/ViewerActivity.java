package com.example.hometaskone.activity;

import android.os.Bundle;

import com.example.hometaskone.R;
import com.example.hometaskone.fragment.ViewerFragment;

public class ViewerActivity extends BasicActivity {
    private String band;
    private String album;
    private int cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            band = getIntent().getStringExtra("band");
            album = getIntent().getStringExtra("album");
            cover = getIntent().getIntExtra("cover", R.drawable.ic_launcher);
            ViewerFragment viewer = new ViewerFragment(band, album, cover);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.viewer_container, viewer)
                    .commit();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_viewer;
    }
}
