package com.example.keddreader.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.keddreader.App;
import com.example.keddreader.R;
import com.example.keddreader.fragment.ArticleFragment;
import com.example.keddreader.model.Article;

public class ArticleActivity extends BaseActivity {

    MenuItem favIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String title = feedSingleton.getCurrentArticle().getTitle();

        if(isTabletLand()){
            // Tablet is rotated to landscape mode, so show main activity
            finish();
        }else{
            // Show current article
            ArticleFragment article = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            article.setSingletonCurrentContent();

            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article, menu);

        favIcon = menu.findItem(R.id.action_save_to_fav);

        if(currentArticleIsFav()){
            favIcon.setIcon(R.drawable.ic_action_important);
        }else{
            favIcon.setIcon(R.drawable.ic_action_not_important);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save_to_fav:
                toggleFavorite(item);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggleFavorite(MenuItem item){

        Article currentArticle = feedSingleton.getCurrentArticle();
        if(!currentArticleIsFav()){
            item.setIcon(R.drawable.ic_action_important);
            App.favDbHelper.addFav(currentArticle);
        }else{
            item.setIcon(R.drawable.ic_action_not_important);
            App.favDbHelper.removeFav(currentArticle);
        }

    }

}
