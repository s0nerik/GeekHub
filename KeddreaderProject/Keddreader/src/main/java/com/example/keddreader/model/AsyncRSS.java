package com.example.keddreader.model;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class AsyncRSS extends AsyncTask<String, Void, Feed>{

    Feed feed = new Feed();
    FeedMessage msg;

    AsyncFeedGetter caller;
    FeedSingleton fs;

    ProgressDialog progDailog;

    public AsyncRSS(AsyncFeedGetter caller, FeedSingleton fs){
        this.caller = caller;
        this.fs = fs;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog((Activity) caller);
        progDailog.setMessage("Loading feed...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    @Override
    protected Feed doInBackground(String... params) {
//        System.out.println("AsyncRSS doInBackground started");
        XmlPullParserFactory factory = null;
        XmlPullParser xpp = null;
        String name;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(getUrlData(params[0])));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if("item".equals(xpp.getName()) && eventType == XmlPullParser.START_TAG){
                    xpp.next();
                    name = xpp.getName();
                    msg = new FeedMessage();
                    while (!"item".equals(name)){
                        if(name != null){
                            switch (name){
                                case "title":
                                    msg.setTitle(xpp.nextText());
                                    break;
                                case "link":
                                    msg.setLink(xpp.nextText());
                                    break;
                                case "guid":
                                    msg.setGuid(xpp.nextText());
                                    break;
                                case "description":
                                    msg.setDescription(xpp.nextText());
                                    break;
                                case "pubDate":
                                    msg.setPubDate(xpp.nextText());
                                    break;
                                case "encoded":
                                    msg.setContent(xpp.nextText());
                                    break;
                            }
                        }
                        xpp.next();
                        name = xpp.getName();
                    }
                    feed.addMessage(msg);
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return feed;
    }

    public InputStream getUrlData(String url)
            throws URISyntaxException, IOException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(new URI(url));
        HttpResponse res = client.execute(method);
        return  res.getEntity().getContent();

    }

    @Override
    protected void onPostExecute(Feed f) {
        super.onPostExecute(f);
        caller.onFeedParsed(f);
        fs.onFeedParsed(f);
        progDailog.dismiss();
    }

}