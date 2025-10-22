package com.myappstore.data;

public class DevsApp {
    private String name;
    private String status;
    private String description; // <-- ADD THIS LINE
    private double price;
    private int views;
    private int downloads;

    // The constructor now includes the description
    public DevsApp(String name, String status, String description, double price, int views, int downloads) {
        this.name = name;
        this.status = status;
        this.description = description; // <-- ADD THIS LINE
        this.price = price;
        this.views = views;
        this.downloads = downloads;
    }

    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getDescription() { return description; } // <-- ADD THIS GETTER
    public double getPrice() { return price; }
    public int getViews() { return views; }
    public int getDownloads() { return downloads; }
}