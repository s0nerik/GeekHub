package com.example.keddreader.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.keddreader.App;
import com.example.keddreader.helper.FavDbHelper;

public class FavProvider extends ContentProvider{

    /*
    *   I think, that using Content Provider in my case is redundant.
    *   But I created it just to show that I CAN do it. :)
    *   (It's working well, but it's really easier for me to use just an SQLOpenHelper.)
    */

    static final String AUTHORITY = "com.example.keddreader.model.FavProvider";
    static final String FAV_PATH = "favorites";
    public static final Uri FAV_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + FAV_PATH);

    static final int URI_FAV = 1;
    static final int URI_FAV_TITLE = 2;

    // Multiple rows
    static final String FAV_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + FAV_PATH;

    // One row
    static final String FAV_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + FAV_PATH;

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, FAV_PATH, URI_FAV);
        uriMatcher.addURI(AUTHORITY, FAV_PATH+"/*", URI_FAV_TITLE);
    }

    @Override
    public boolean onCreate() {
//        Log.d("KEDD", "FavProvider onCreate");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch(uriMatcher.match(uri)){
            case URI_FAV:
                break;
            case URI_FAV_TITLE:
                String title = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FavDbHelper.TITLE + " = \"" + title + "\"";
                } else {
                    selection = selection + " AND " + FavDbHelper.TITLE + " = " + title;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        Cursor cursor = App.readableFavDB.query(FavDbHelper.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), FAV_CONTENT_URI);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        switch(uriMatcher.match(uri)){
            case URI_FAV:
                return FAV_CONTENT_TYPE;
            case URI_FAV_TITLE:
                return FAV_CONTENT_ITEM_TYPE;
        }

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (uriMatcher.match(uri) != URI_FAV)
            throw new IllegalArgumentException("Wrong URI: " + uri);
        long rowID = App.writableFavDB.insert(FavDbHelper.TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(FAV_CONTENT_URI, rowID);
        // notify ContentResolver that data in resultUri changed
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch(uriMatcher.match(uri)){
            case URI_FAV:
                break;
            case URI_FAV_TITLE:
                String title = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FavDbHelper.TITLE + " = \"" + title + "\"";
                } else {
                    selection = selection + " AND " + FavDbHelper.TITLE + " = " + title;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        int count = App.writableFavDB.delete(FavDbHelper.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch(uriMatcher.match(uri)){
            case URI_FAV:
                break;
            case URI_FAV_TITLE:
                String title = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FavDbHelper.TITLE + " = \"" + title + "\"";
                } else {
                    selection = selection + " AND " + FavDbHelper.TITLE + " = \"" + title + "\"";
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        int count = App.writableFavDB.update(FavDbHelper.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
