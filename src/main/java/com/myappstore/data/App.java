package com.myappstore.data;

public class App {
    private String title;
    private String category;
    private String rating;
    private String description;
    private String imageUrl;
    private double price; // <-- NEW FIELD

    public App(String title, String category, String rating, String description, String imageUrl, double price) {
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price; // <-- NEW FIELD
    }

    // Getters
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; } // <-- NEW GETTER
}