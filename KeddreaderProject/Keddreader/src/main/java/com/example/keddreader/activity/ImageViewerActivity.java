package com.example.keddreader.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.keddreader.R;
import com.example.keddreader.custom_view.TouchImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class ImageViewerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image_viewer);

        String url = getIntent().getStringExtra("url");

        TouchImageView imageView = (TouchImageView) findViewById(R.id.image_viewer);

        final ProgressBar spinner = (ProgressBar) findViewById(R.id.image_viewer_spinner);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, options,  new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                spinner.setVisibility(View.GONE);
            }
        });
    }
}