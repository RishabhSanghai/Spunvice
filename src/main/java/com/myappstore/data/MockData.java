package com.myappstore.data;

import com.vaadin.flow.component.icon.VaadinIcon;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MockData {

    // --- Data for the main storefront ---
    private static final List<App> STORE_APPS = new ArrayList<>(List.of(
        new App("Photo Editor Pro", "Productivity", "4.8", 
            "A professional-grade photo editing suite with AI-powered tools. Features include advanced color correction, layer-based editing, RAW file support, and over 200 professional filters. Perfect for photographers and content creators who need precision editing capabilities on the go.",
            "https://picsum.photos/id/250/64/64", 4.99),
        
        new App("Galaxy Runner", "Games", "4.5", 
            "Embark on an interstellar adventure in this fast-paced endless runner! Navigate through asteroid fields, collect cosmic energy crystals, and unlock powerful spaceships. With stunning visuals and intuitive swipe controls, Galaxy Runner offers hours of addictive gameplay across 50+ challenging levels.",
            "https://picsum.photos/id/1062/64/64", 0.0),
        
        new App("Finance Tracker", "Finance", "4.9", 
            "Take control of your financial future with our comprehensive money management solution. Track expenses across multiple accounts, set savings goals, monitor investments, and generate detailed financial reports. Features bank-level security with 256-bit encryption and automatic transaction categorization.",
            "https://picsum.photos/id/257/64/64", 9.99),
        
        new App("Cloud Stream", "Music & Audio", "4.2", 
            "Access over 80 million songs and 3 million podcasts with our premium streaming service. Create personalized playlists, discover new artists through AI recommendations, and download music for offline listening. High-fidelity audio streaming and cross-platform synchronization included.",
            "https://picsum.photos/id/1080/64/64", 0.0),
        
        new App("QuickNote", "Productivity", "4.7", 
            "The ultimate note-taking experience designed for speed and organization. Capture ideas instantly with voice-to-text, sketch diagrams with finger drawing, and organize notes with tags and notebooks. Features end-to-end encryption and seamless sync across all your devices.",
            "https://picsum.photos/id/292/64/64", 0.0),
        
        new App("Dungeon Quest", "Games", "3.8", 
            "Dive into a dark fantasy world filled with ancient mysteries and terrifying monsters. Build your character from 6 unique classes, master hundreds of spells and abilities, and explore procedurally generated dungeons. Multiplayer co-op support for up to 4 players with cross-platform play.",
            "https://picsum.photos/id/431/64/64", 1.99),
        
        new App("ConnectSphere", "Social", "4.4", 
            "Revolutionize your social connections with our privacy-focused platform. Share moments with granular privacy controls, join interest-based communities, and discover local events. End-to-end encrypted messaging and video calls for up to 50 participants simultaneously.",
            "https://picsum.photos/id/64/64/64", 0.0),
        
        new App("Weather Now", "Utilities", "3.9", 
            "Get hyper-accurate weather forecasts with stunning visualizations. Features include severe weather alerts, pollen and air quality indexes, hurricane tracking, and historical weather data. Customizable widgets and detailed meteorological data for weather enthusiasts.",
            "https://picsum.photos/id/998/64/64", 0.99),
        
        new App("TaskMaster", "Productivity", "3.2", 
            "Transform your productivity with intelligent task management. Create projects, set deadlines, assign tasks to team members, and track progress with Kanban boards and Gantt charts. Integration with calendar apps and smart reminders based on your location and time patterns.",
            "https://picsum.photos/id/355/64/64", 2.99),
        
        new App("Pixel Racers", "Games", "2.5", 
            "Experience retro racing reimagined for modern devices! Choose from 25 customizable cars, race across 15 vibrant tracks, and compete in global tournaments. Local multiplayer support for up to 4 players on the same device with controller compatibility.",
            "https://picsum.photos/id/163/64/64", 0.0),
        
        new App("CoinWise", "Finance", "2.9", 
            "Your comprehensive cryptocurrency companion. Track 5000+ coins in real-time, set price alerts, monitor your portfolio performance, and execute trades across multiple exchanges. Advanced charting tools and news aggregation from top crypto sources.",
            "https://picsum.photos/id/326/64/64", 0.0),
        
        new App("Echo Chamber", "Music & Audio", "4.0", 
            "Create professional-quality music anywhere with our mobile studio. Features multi-track recording, 50+ virtual instruments, 200+ sound effects, and AI-powered mastering. Export in multiple formats including WAV, MP3, and direct sharing to streaming platforms.",
            "https://picsum.photos/id/103/64/64", 5.99)
    ));
    
    // --- Data for the Creator Dashboard ---
    private static final List<DevsApp> DEVS_APPS = new ArrayList<>(List.of(
        new DevsApp("Galaxy Runner", "Live", "An endless runner game set in space with stunning visuals and addictive gameplay.", 0.0, 15234, 4890),
        new DevsApp("Dungeon Quest", "Live", "A dark fantasy RPG with procedurally generated dungeons and multiplayer support.", 1.99, 8950, 1230)
    ));
    private static final List<Transaction> TRANSACTIONS = new ArrayList<>(List.of(
        new Transaction(LocalDate.now().minusDays(5), "Sale of Dungeon Quest", 1.39),
        new Transaction(LocalDate.now().minusDays(4), "Sale of Dungeon Quest", 1.39),
        new Transaction(LocalDate.now().minusDays(2), "Sale of Dungeon Quest", 1.39)
    ));
    private static final List<DevPost> DEVS_POSTS = new ArrayList<>(List.of(
        new DevPost("Version 1.1 Patch Notes", "Published", LocalDate.now().minusDays(7), 1204, 88),
        new DevPost("Upcoming Features - A Sneak Peek!", "Published", LocalDate.now().minusDays(25), 3480, 215),
        new DevPost("Our Next Big Project (Draft)", "Draft", LocalDate.now(), 0, 0)
    ));
    private static double currentBalance = 4.17;

    // --- Methods for the storefront ---
    public static List<App> getAllApps() { return STORE_APPS; }
    public static App getFeaturedApp() { return STORE_APPS.get(1); }
    public static List<App> getAppByTitle(String title) {
        if (title == null || title.isEmpty()) { return STORE_APPS; }
        return STORE_APPS.stream().filter(app -> app.getTitle().equalsIgnoreCase(title)).toList();
    }
    public static List<App> getFilteredApps(String category, String priceFilter, double minRating) {
        Stream<App> appStream = STORE_APPS.stream();
        if (category != null && !category.equals("All")) { appStream = appStream.filter(app -> app.getCategory().equalsIgnoreCase(category)); }
        if (priceFilter != null && !priceFilter.equals("All")) {
            if (priceFilter.equals("Free")) { appStream = appStream.filter(app -> app.getPrice() == 0.0); } 
            else if (priceFilter.equals("Paid")) { appStream = appStream.filter(app -> app.getPrice() > 0.0); }
        }
        if (minRating > 0) { appStream = appStream.filter(app -> Double.parseDouble(app.getRating()) >= minRating); }
        return appStream.toList();
    }
    
    public static Map<String, VaadinIcon> getTopCategories() {
        Map<String, VaadinIcon> categories = new LinkedHashMap<>();
        categories.put("Games", VaadinIcon.GAMEPAD);
        categories.put("Productivity", VaadinIcon.DESKTOP);
        categories.put("Finance", VaadinIcon.WALLET);
        categories.put("Social", VaadinIcon.USERS);
        categories.put("Utilities", VaadinIcon.TOOLS);
        return categories;
    }

    // --- Methods for the dashboard ---
    public static List<DevsApp> getDevsApps() { return DEVS_APPS; }
    public static List<Transaction> getTransactions() { return TRANSACTIONS; }
    public static double getCurrentBalance() { return currentBalance; }
    public static List<DevPost> getDevsPosts() { return DEVS_POSTS; }
    public static void processPayout() {
        if (currentBalance > 0) { TRANSACTIONS.add(0, new Transaction(LocalDate.now(), "Payout to Bank Account", -currentBalance)); currentBalance = 0.0; }
    }
    public static void addPost(DevPost post) { DEVS_POSTS.add(0, post); }
    public static void addDevsApp(DevsApp app) { DEVS_APPS.add(0, app); }
}