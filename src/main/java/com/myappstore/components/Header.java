package com.myappstore.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Header extends HorizontalLayout {
    public Header() {
        addClassName("header");
        setWidthFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout logoContainer = new HorizontalLayout();
        logoContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        Image appIcon = new Image("https://i.imgur.com/rGO3y9a.png", "Spunvice Logo");
        appIcon.addClassName("header-logo");
        H1 title = new H1("Spunvice");
        logoContainer.add(appIcon, title);
        
        Div navLinks = new Div();
        navLinks.addClassName("nav-links");
        navLinks.add(
            new Anchor("/store", "App Store"),
            new Anchor("/dev-portal", "Developer Portal"),
            new Anchor("/", "Logout")
        );
        
        add(logoContainer, navLinks);
    }
}