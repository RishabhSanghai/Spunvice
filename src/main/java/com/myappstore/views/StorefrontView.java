package com.myappstore.views;

import com.myappstore.components.AppGrid;
import com.myappstore.components.Header;
import com.myappstore.components.HeroSection;
import com.myappstore.components.Sidebar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("store") // Changed from "" to "store"
public class StorefrontView extends VerticalLayout {

    private final AppGrid appGrid;
    private final Sidebar sidebar;

    public StorefrontView() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();

        Header header = new Header();

        Div contentWrapper = new Div();
        contentWrapper.addClassName("main-content-wrapper");
        contentWrapper.setWidthFull();

        appGrid = new AppGrid();
        sidebar = new Sidebar();

        // Listener for the main filters (category, price, rating)
        sidebar.addFilterChangeListener(event -> {
            appGrid.filterApps(event.getCategory(), event.getPrice(), event.getRating());
        });

        // GUARANTEED LISTENER for the App of the Day click
        sidebar.addAppOfTheDayClickListener(title -> {
            appGrid.showSingleApp(title);
        });

        Div mainContent = new Div();
        mainContent.addClassName("main-content");
        mainContent.add(new HeroSection(), appGrid);
        
        contentWrapper.add(mainContent, sidebar);
        
        add(header, contentWrapper);
    }
}