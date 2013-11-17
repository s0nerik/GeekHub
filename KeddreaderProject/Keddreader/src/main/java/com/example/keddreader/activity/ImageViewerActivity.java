package com.example.keddreader.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.keddreader.R;
import com.example.keddreader.custom_view.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageViewerActivity extends ActionBarActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image_viewer);

        url = getIntent().getStringExtra("url");

        TouchImageView imageView = (TouchImageView) findViewById(R.id.image_viewer);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage(url, imageView);
    }

}