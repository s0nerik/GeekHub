package com.example.keddreader.model;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class AsyncRssCheck extends AsyncTask<String, Void, Void> {

    private AsyncFeedChecker caller;
    private String lastPubDate;

    public AsyncRssCheck(AsyncFeedChecker caller){
        this.caller = caller;
    }

    @Override
    protected Void doInBackground(String... params) {
        XmlPullParserFactory factory;
        XmlPullParser xpp;
        try {

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(getUrlData(params[0])));
            do{
                xpp.next();
            }while(!"pubDate".equals(xpp.getName()));
            lastPubDate = xpp.nextText();

        } catch (XmlPullParserException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getUrlData(String url)
            throws URISyntaxException, IOException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(new URI(url));
        HttpResponse res = client.execute(method);
        return  res.getEntity().getContent();

    }

    @Override
    protected void onPostExecute(Void a) {
        super.onPostExecute(a);
        caller.onFeedChecked(lastPubDate);
    }

}