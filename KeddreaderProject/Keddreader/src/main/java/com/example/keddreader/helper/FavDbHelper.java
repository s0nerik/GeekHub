package com.example.keddreader.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.keddreader.App;
import com.example.keddreader.model.Article;

public class FavDbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "test";

    public static final String TABLE_NAME = "favorites";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String AUTHOR = "author";
    public static final String DATE = "pubDate";
    public static final String LINK = "link";

    public static final int TITLE_ID = 1;
    public static final int CONTENT_ID = 2;
    public static final int DATE_ID = 3;
    public static final int AUTHOR_ID = 4;
    public static final int LINK_ID = 5;

    private static final String CREATE_TABLE = "create table "
            + TABLE_NAME + " ( _id integer primary key autoincrement, "
            + TITLE + " TEXT, "
            + CONTENT + " TEXT, "
            + DATE + " TEXT, "
            + AUTHOR + " TEXT, "
            + LINK + " TEXT)";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public boolean isFav(Article article){
        // Select first record from table with article's title.
        // If selected items count isn't 0, then the article is favorite.
        // Hope, there's no duplicates in DB :)
        Cursor mCount= App.readableFavDB.rawQuery("SELECT EXISTS(SELECT 1 FROM " + TABLE_NAME + " WHERE " + TITLE + "=?)", new String[]{article.getTitle()});
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        return count != 0;
    }

    public void addFav(Article article){
        ContentValues cv = new ContentValues();
        cv.put(TITLE, article.getTitle());
        cv.put(CONTENT, article.getContent());
        cv.put(DATE, article.getPubDate());
        cv.put(AUTHOR, article.getAuthor());
        cv.put(LINK, article.getLink());
        App.writableFavDB.insert(TABLE_NAME, null, cv);
    }

    public Article getArticleByTitle(String title){
        // Select record with specified title
        Cursor c = App.readableFavDB.query(TABLE_NAME, null, TITLE+"=?", new String[]{title}, null, null, null);

        // Query will never return null, so we don't need to check it for null.
        // But it can return an empty Cursor, so we need to handle it.
        if(c.getCount() != 0){
            c.moveToFirst();
            String articleTitle = c.getString(TITLE_ID);
            String articleContent = c.getString(CONTENT_ID);
            String articlePubDate = c.getString(DATE_ID);
            String articleAuthor = c.getString(AUTHOR_ID);
            String articleLink = c.getString(LINK_ID);
            c.close();
            return new Article(articleTitle, articleContent, articlePubDate, articleAuthor, articleLink);
        }else{
            c.close();
            Log.e("KEDD", "No such article: " + title);
            return null;
        }
    }

    public Cursor getFavs(){
        return App.readableFavDB.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public String[] getFavTitles(){
        Cursor c = App.readableFavDB.query(TABLE_NAME, new String[]{TITLE}, null, null, null, null, null);
        String[] s = new String[c.getCount()];
        if(c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                int i = 0;
                do{
                    s[i] = c.getString(0);
                    i++;
                }while(c.moveToNext());
            }
            c.close();
        }
        return s;
    }

    public void removeFav(Article article){
        App.writableFavDB.delete(TABLE_NAME, TITLE+"=?", new String[]{article.getTitle()});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}