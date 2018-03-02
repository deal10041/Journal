package com.gmail.deal10041.journal;

import java.io.Serializable;

/**
 * Created by Dylan Wellner on 27-2-2018.
 * Model of a journal entry
 */

public class JournalEntry implements Serializable{

    private int id;
    private String title;
    private String content;
    private String mood;
    private String timestamp;
    private boolean starred = false;

    public JournalEntry(String aTitle, String aContent, String aMood) {
        this.title = aTitle;
        this.content = aContent;
        this.mood = aMood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
}
