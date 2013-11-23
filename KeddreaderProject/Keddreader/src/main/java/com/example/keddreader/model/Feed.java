package com.example.keddreader.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private String[] titles;
    private String[] contents;
    private String[] authors;
    private String[] pubDates;

    final ArrayList<Article> entries = new ArrayList<>();

    public List<Article> getMessages() {
        return entries;
    }

    public void addMessage(Article msg){
        entries.add(msg);
    }

    public String[] getTitles() {
        return titles;
    }

    public String[] getContents() {
        return contents;
    }

    public String[] getAuthors(){
        return authors;
    }

    public String[] getPubDates(){
        return pubDates;
    }

    private void setInnerTitles(){
        String[] titles = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            titles[i] = entries.get(i).getTitle();
        }
        this.titles = titles;
    }

    private void setInnerContents(){
        String[] contents = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            contents[i] = entries.get(i).getContent();
        }
        this.contents = contents;
    }

    private void setInnerAuthors(){
        String[] authors = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            authors[i] = entries.get(i).getAuthor();
        }
        this.authors = authors;
    }

    private void setInnerPubDates(){
        String[] pubDates = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            pubDates[i] = entries.get(i).getPubDate();
        }
        this.pubDates = pubDates;
    }

    public void setInnerData(){
        setInnerTitles();
        setInnerContents();
        setInnerPubDates();
        setInnerAuthors();
    }

}
