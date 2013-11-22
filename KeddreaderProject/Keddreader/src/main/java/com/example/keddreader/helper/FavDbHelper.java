package com.example.keddreader.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.keddreader.App;

public class FavDbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test";
    public static final String TABLE_NAME = "favorites";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String AUTHOR = "author";
    public static final String DATE = "pubDate";
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

    public boolean isFav(String title){
        Cursor mCount= App.readableFavDB.rawQuery("SELECT EXISTS(SELECT 1 FROM " + TABLE_NAME + " WHERE " + TITLE + "=?)", new String[]{title});
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        return count != 0;
    }

    public void addFav(String title, String content, String author, String date){
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(CONTENT, content);
        cv.put(DATE, date);
        cv.put(AUTHOR, author);
        App.writableFavDB.insert(TABLE_NAME, null, cv);
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

    public void removeFav(String title){
        App.writableFavDB.delete(TABLE_NAME, TITLE+"=?", new String[]{title});
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