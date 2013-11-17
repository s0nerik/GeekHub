package com.example.keddreader.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class FeedMessage {

    private String title;
    private String description;
    private String link;
    private String guid;
    private String pubDate;
    private String content;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setContent(String cnt){
        content = "<html><head><style type=\"text/css\">img { max-width: 100%; }</style></head><body>"
                +cnt
                +"</body></html>";
        // Parse resulting html
        Document doc = Jsoup.parse(content);

        // Remove "width" and "height" attributes of images to make them fit the screen
        Elements imgs = doc.body().select("img");
        imgs.removeAttr("width");
        imgs.removeAttr("height");

        for(Element e:imgs){
            e.wrap("<a href=\""+e.attr("src")+"\"></a>");
        }

        // Replace videos with links on these videos
        Elements iframes = doc.body().select("iframe");
        String src;
        for(Element iframe:iframes){

            // Replace wrong "//..." src's with "http://"
            src = "http:"+iframe.attr("src");

            // Replace iframes with links
            iframe.replaceWith(new Element(Tag.valueOf("a"), "").attr("href", src).text("Видео"));
        }
          
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
