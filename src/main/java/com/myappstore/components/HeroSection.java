package com.myappstore.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HeroSection extends VerticalLayout {

    public HeroSection() {
        addClassName("hero-section");
        setAlignItems(Alignment.CENTER);
        setPadding(false);

        H2 title = new H2("Welcome to Spunvice");
        Paragraph description = new Paragraph("Discover innovative apps, creative tools, and cutting-edge software from developers worldwide.");

        add(title, description);
    }
}