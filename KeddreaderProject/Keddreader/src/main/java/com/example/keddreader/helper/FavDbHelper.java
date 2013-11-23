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

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test";
    public static final String TABLE_NAME = "favorites";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String AUTHOR = "author";
    public static final String DATE = "pubDate";
    public static final int TITLE_ID = 1;
    public static final int CONTENT_ID = 2;
    public static final int DATE_ID = 3;
    public static final int AUTHOR_ID = 4;
    private static final String CREATE_TABLE = "create table " + TABLE_NAME
            + " ( _id integer primary key autoincrement, "
            + TITLE + " TEXT, "
            + CONTENT + " TEXT, "
            + DATE + " TEXT, "
            + AUTHOR + " TEXT)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public boolean isFav(Article article){
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
        App.writableFavDB.insert(TABLE_NAME, null, cv);
    }

    public Article getArticleByTitle(String title){
        Cursor c = App.readableFavDB.query(TABLE_NAME, null, TITLE+"=?", new String[]{title}, null, null, null);
        if(c != null && c.getCount() != 0){
            c.moveToFirst();
            String articleTitle = c.getString(TITLE_ID);
            String articleContent = c.getString(CONTENT_ID);
            String articlePubDate = c.getString(DATE_ID);
            String articleAuthor = c.getString(AUTHOR_ID);
            c.close();
            return new Article(articleTitle, articleContent, articlePubDate, articleAuthor);
        }else{
            Log.d("KEDD", "No such article: "+title);
            return null;
        }
    }

    public Cursor getFavs(){
        return App.readableFavDB.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public String[] getFavContents(){
        Cursor c = App.readableFavDB.query(TABLE_NAME, new String[]{CONTENT}, null, null, null, null, null);
        String[] s = new String[c.getCount()];
        if(c != null && c.getCount() != 0){
            c.moveToFirst();
            int i = 0;
            do{
                s[i] = c.getString(0);
                i++;
            }while(c.moveToNext());
        }
        return s;
    }

    public String[] getFavTitles(){
        Cursor c = App.readableFavDB.query(TABLE_NAME, new String[]{TITLE}, null, null, null, null, null);
        String[] s = new String[c.getCount()];
        if(c != null && c.getCount() != 0){
            c.moveToFirst();
            int i = 0;
            do{
                s[i] = c.getString(0);
                i++;
            }while(c.moveToNext());
        }
        return s;
    }

    public String[] getFavAuthors(){
        Cursor c = App.readableFavDB.query(TABLE_NAME, new String[]{AUTHOR}, null, null, null, null, null);
        String[] s = new String[c.getCount()];
        if(c != null && c.getCount() != 0){
            c.moveToFirst();
            int i = 0;
            do{
                s[i] = c.getString(0);
                i++;
            }while(c.moveToNext());
        }
        return s;
    }

    public String[] getFavPubDates(){
        Cursor c = App.readableFavDB.query(TABLE_NAME, new String[]{DATE}, null, null, null, null, null);
        String[] s = new String[c.getCount()];
        if(c != null && c.getCount() != 0){
            c.moveToFirst();
            int i = 0;
            do{
                s[i] = c.getString(0);
                i++;
            }while(c.moveToNext());
        }
        return s;
    }

    public void printCurrState(){
        Cursor c = null;
        c = App.readableFavDB.query(TABLE_NAME, null, null, null, null, null, null);

        if (c != null) {
            Log.d("KEDD", "-----DB OUTPUT-----");
            if (c.moveToFirst()) {
                String str;
                do {
                    Log.d("KEDD", "// Columns \\\\");
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        Log.d("KEDD", str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "\n"));
                    }
                    Log.d("KEDD", "\\\\ Columns //");
                } while (c.moveToNext());
            }
            c.close();
            Log.d("KEDD", "-----/DB OUTPUT-----");
        } else
            Log.d("KEDD", "Cursor is null");

    }

    public void printCursorData(Cursor c){

        if (c != null) {
            Log.d("KEDD", "-----Cursor data-----");
            if (c.moveToFirst()) {
                String str;
                do {
                    Log.d("KEDD", "// Columns \\\\");
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        Log.d("KEDD", str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "\n"));
                    }
                    Log.d("KEDD", "\\\\ Columns //");
                } while (c.moveToNext());
            }
            c.close();
            Log.d("KEDD", "-----/Cursor data-----");
        } else
            Log.d("KEDD", "Cursor is null");

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