package com.myappstore.data;

import java.time.LocalDate;

// A simple class to hold data about a developer's blog post
public class DevPost {
    private String title;
    private String status;
    private LocalDate publishedDate;
    private int views;
    private int likes;

    public DevPost(String title, String status, LocalDate publishedDate, int views, int likes) {
        this.title = title;
        this.status = status;
        this.publishedDate = publishedDate;
        this.views = views;
        this.likes = likes;
    }

    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public int getViews() { return views; }
    public int getLikes() { return likes; }
}