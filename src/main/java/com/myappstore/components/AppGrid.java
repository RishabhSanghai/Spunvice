package com.myappstore.components;

import com.myappstore.data.App;
import com.myappstore.data.MockData;
import com.vaadin.flow.component.html.Div;
import java.util.List;

public class AppGrid extends Div {

    public AppGrid() {
        addClassName("app-grid");
        updateApps(MockData.getAllApps());
    }

    // Method for the main multi-filter system
    public void filterApps(String category, String priceFilter, double minRating) {
        List<App> filteredApps = MockData.getFilteredApps(category, priceFilter, minRating);
        updateApps(filteredApps);
    }
    
    // --- NEW METHOD ---
    // Method specifically for showing one app by its title
    public void showSingleApp(String title) {
        List<App> singleAppList = MockData.getAppByTitle(title);
        updateApps(singleAppList);
    }
    
    private void updateApps(List<App> apps) {
        removeAll();
        for (App app : apps) {
            add(new AppCard(app));
        }
    }
}