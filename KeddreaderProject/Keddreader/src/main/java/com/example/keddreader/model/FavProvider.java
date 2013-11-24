//package com.example.keddreader.model;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.util.Log;
//
//import com.example.keddreader.App;
//
//public class FavProvider extends ContentProvider {
//    final String LOG_TAG = "KEDD";
//
//    // // Uri
//    // authority
//    static final String AUTHORITY = "com.example.keddreader";
//
//    // path
//    static final String FAV_PATH = "favorites";
//
//    // Общий Uri
//    public static final Uri FAV_CONTENT_URI = Uri.parse("content://"
//            + AUTHORITY + "/" + FAV_PATH);
//
//    // Типы данных
//    // набор строк
//    static final String FAV_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
//            + AUTHORITY + "." + FAV_PATH;
//
//    // одна строка
//    static final String FAV_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
//            + AUTHORITY + "." + FAV_PATH;
//
//    //// UriMatcher
//    // общий Uri
//    static final int URI_FAVS = 1;
//
//    // Uri с указанным ID
//    static final int URI_FAVS_ID = 2;
//
//    // описание и создание UriMatcher
//    private static final UriMatcher uriMatcher;
//    static {
//        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(AUTHORITY, FAV_PATH, URI_FAVS);
//        uriMatcher.addURI(AUTHORITY, FAV_PATH + "/#", URI_FAVS_ID);
//    }
//
//    SQLiteDatabase db = App.writableFavDB;
//
//    @Override
//    public boolean onCreate() {
//        return false;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        return null;
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        return null;
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        return null;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }
//}