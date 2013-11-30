package com.example.keddreader.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class Article {

    private String title;
    private String content;
    private String pubDate;
    private String author;

    public Article(){}

    public Article(String title, String content, String pubDate, String author){
        this.title = title;
        this.content = content;
        this.pubDate = pubDate;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate.substring(5, pubDate.lastIndexOf(":"));
    }

    public void setContent(String cnt){

        // Set tags that we need
        Whitelist whitelist = new Whitelist();
        whitelist.addTags("img", "a", "h2", "p", "li", "ul");
        whitelist.addAttributes("a", "href");
        whitelist.addAttributes("img", "src");
        whitelist.addAttributes("iframe", "src");

        // Let's remove all unnecessary tags and attributes
        cnt = Jsoup.clean(cnt, whitelist);

        // Parse (invalid) content html
        // Jsoup will automatically make HTML valid
        Document doc = Jsoup.parse(cnt);

        Elements imgs = doc.body().select("img");

        // Wrap images, that isn't already inside <a></a> for viewing them in image viewer
        for(Element e:imgs){
            if(!"a".equals(e.parent().tagName())){
                e.wrap("<a href=\""+e.attr("src")+"\"></a>");
            }
        }

        // Replace videos with links on these videos
        Elements iframes = doc.body().select("iframe");
        String src;
        for(Element iframe:iframes){

            // Replace wrong( in Android :) ) "//..." src's with "http://"
            src = "http:"+iframe.attr("src");

            // Replace iframes with images
            iframe.replaceWith(new Element(Tag.valueOf("a"), "").attr("href", src).append("<img src=\"file:///android_asset/video_icon.png\" />"));
        }

        // Fit screen's width and center all images
        doc.head().append("<style type=\"text/css\">img { max-width: 100%; display: block; margin-left: auto; margin-right: auto }</style>");

        content = doc.html();
    }

    public String getContent(){
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
