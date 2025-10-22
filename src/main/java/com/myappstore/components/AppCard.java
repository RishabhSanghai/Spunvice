package com.myappstore.components;

import com.myappstore.data.App;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AppCard extends Div {
    public AppCard(App app) {
        addClassName("app-card");

        // Create a clickable content area (everything except the install button)
        Div clickableContent = new Div();
        clickableContent.addClassName("clickable-content");
        clickableContent.addClickListener(event -> {
            UI.getCurrent().navigate("app-detail/" + app.getTitle());
        });

        // --- Card Header ---
        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        Image appIcon = new Image(app.getImageUrl(), "App Icon");
        appIcon.addClassName("app-card-icon");
        VerticalLayout titleLayout = new VerticalLayout();
        titleLayout.setPadding(false);
        titleLayout.setSpacing(false);
        titleLayout.add(new H3(app.getTitle()), new Span(app.getCategory()));
        header.add(appIcon, titleLayout);

        // --- Rating ---
        Div ratingDiv = new Div(VaadinIcon.STAR.create(), new Span(app.getRating()));
        ratingDiv.addClassName("app-card-rating");

        // --- Description ---
        Paragraph appDescription = new Paragraph(app.getDescription());
        appDescription.addClassName("app-card-description");

        // Add everything except install button to clickable area
        clickableContent.add(header, ratingDiv, appDescription);

        // --- Footer with Price and Button ---
        HorizontalLayout footer = new HorizontalLayout();
        footer.addClassName("app-card-footer");
        
        Span price = new Span(app.getPrice() == 0.0 ? "Free" : String.format("$%.2f", app.getPrice()));
        price.addClassName("app-card-price");
        
        Button installButton = new Button("Install");
        installButton.addClassName("install-button");
        installButton.addClickListener(event -> {
            // Install logic here
            Notification.show(
                "Installing " + app.getTitle() + "...", 
                3000, 
                Notification.Position.MIDDLE
            );
        });
        
        footer.add(price, installButton);
        
        // Add all sections to the card
        add(clickableContent, footer);
    }
}