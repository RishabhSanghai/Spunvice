package com.myappstore.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class PromotionsTabContent extends VerticalLayout {

    private final Div promotionForm;
    private final VerticalLayout saleListContainer;
    private final Button createSaleButton;

    public PromotionsTabContent() {
        addClassName("promotions-tab");
        
        Paragraph info = new Paragraph("A sale lets you set a time period where some of your projects are available for a reduced price.");
        createSaleButton = new Button("Create a new sale or bundle");
        createSaleButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        
        // This container will hold the list of sales we create
        saleListContainer = new VerticalLayout();
        saleListContainer.setPadding(false);
        
        // Create the form (initially hidden)
        promotionForm = createPromotionForm();

        createSaleButton.addClickListener(event -> {
            promotionForm.setVisible(true);
            createSaleButton.setVisible(false);
        });

        add(info, createSaleButton, saleListContainer, promotionForm);
    }
    
    private Div createPromotionForm() {
        Div form = new Div();
        form.addClassName("promotion-form");
        form.setVisible(false);

        H3 formTitle = new H3("New Sale / Bundle Details");
        TextField promotionName = new TextField("Promotion Name");
        NumberField discountPercentage = new NumberField("Discount Percentage (%)");
        DatePicker startDate = new DatePicker("Start Date");
        DatePicker endDate = new DatePicker("End Date");

        Button saveButton = new Button("Save Promotion", event -> {
            String name = promotionName.getValue();
            if (name.isEmpty()) {
                Notification.show("Please enter a promotion name.");
                return;
            }
            // Create a UI component to represent the new sale
            Paragraph newSale = new Paragraph("Active Sale: '" + name + "' (" + discountPercentage.getValue() + "% off)");
            saleListContainer.add(newSale); // Add it to our list
            
            hidePromotionForm(promotionName, discountPercentage, startDate, endDate);
            Notification.show("Sale created successfully!");
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancel", event -> hidePromotionForm(promotionName, discountPercentage, startDate, endDate));

        form.add(formTitle, promotionName, discountPercentage, startDate, endDate, saveButton, cancelButton);
        return form;
    }

    private void hidePromotionForm(TextField name, NumberField discount, DatePicker start, DatePicker end) {
        promotionForm.setVisible(false);
        createSaleButton.setVisible(true);
        name.clear();
        discount.clear();
        start.clear();
        end.clear();
    }
}