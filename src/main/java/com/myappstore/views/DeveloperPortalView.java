package com.myappstore.views;

import com.myappstore.components.AnalyticsTabContent;
import com.myappstore.components.EarningsTabContent;
import com.myappstore.components.PostsTabContent;
import com.myappstore.components.ProjectsTabContent;
import com.myappstore.components.PromotionsTabContent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import java.util.HashMap;
import java.util.Map;

@Route("dev-portal")
public class DeveloperPortalView extends VerticalLayout {

    public DeveloperPortalView() {
        setSizeFull();
        setPadding(false);
        addClassName("dev-portal-view");

        // Custom header for developer portal with navigation
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);

        H1 title = new H1("Spunvice Creator Hub");
        title.addClassName("dashboard-title");

        Div navLinks = new Div();
        navLinks.addClassName("nav-links");
        navLinks.add(
            new Anchor("/store", "App Store"),
            new Anchor("/", "Logout")
        );

        header.add(title, navLinks);

        Tab projectsTab = new Tab("Projects");
        Tab analyticsTab = new Tab("Analytics");
        Tab earningsTab = new Tab("Earnings");
        Tab promotionsTab = new Tab("Promotions");
        Tab postsTab = new Tab("Posts");
        Tabs tabs = new Tabs(projectsTab, analyticsTab, earningsTab, promotionsTab, postsTab);
        tabs.addClassName("dashboard-tabs");

        Component projectsContent = new ProjectsTabContent(); 
        Component analyticsContent = new AnalyticsTabContent();
        Component earningsContent = new EarningsTabContent();
        Component promotionsContent = new PromotionsTabContent();
        Component postsContent = new PostsTabContent();

        analyticsContent.setVisible(false);
        earningsContent.setVisible(false);
        promotionsContent.setVisible(false);
        postsContent.setVisible(false);
        
        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(projectsTab, projectsContent);
        tabsToPages.put(analyticsTab, analyticsContent);
        tabsToPages.put(earningsTab, earningsContent);
        tabsToPages.put(promotionsTab, promotionsContent);
        tabsToPages.put(postsTab, postsContent);

        Div contentContainer = new Div(projectsContent, analyticsContent, earningsContent, promotionsContent, postsContent);
        contentContainer.addClassName("dashboard-content");
        contentContainer.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        add(header, tabs, contentContainer);
        expand(contentContainer);
    }
}