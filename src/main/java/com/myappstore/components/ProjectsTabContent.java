package com.myappstore.components;

import com.myappstore.data.DevsApp;
import com.myappstore.data.MockData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ProjectsTabContent extends VerticalLayout {
    private Grid<DevsApp> grid;

    public ProjectsTabContent() {
        setPadding(false);
        Dialog submitDialog = createSubmitDialog();
        Button submitNewAppButton = new Button("Submit New App", VaadinIcon.UPLOAD.create());
        submitNewAppButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitNewAppButton.addClickListener(event -> submitDialog.open());
        grid = new Grid<>(DevsApp.class, false);
        grid.addClassName("projects-grid");
        grid.addColumn(DevsApp::getName).setHeader("App Name");
        grid.addColumn(DevsApp::getStatus).setHeader("Status");
        grid.addColumn(DevsApp::getPrice).setHeader("Price");
        grid.addColumn(DevsApp::getViews).setHeader("Views");
        grid.addColumn(DevsApp::getDownloads).setHeader("Downloads");
        grid.setItems(MockData.getDevsApps());
        add(submitNewAppButton, grid);
    }

    private Dialog createSubmitDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Submit New Application");
        TextField appName = new TextField("App Name");
        TextArea description = new TextArea("Description"); // <-- ADDED
        NumberField price = new NumberField("Price");
        price.setPrefixComponent(new Span("$"));
        FormLayout formLayout = new FormLayout(appName, price, description);
        formLayout.setColspan(description, 2); // Make it span full width
        dialog.add(formLayout);
        Button submitButton = new Button("Submit", event -> {
            String nameValue = appName.getValue();
            String descValue = description.getValue(); // <-- GET VALUE
            Double priceValue = price.getValue();
            if (nameValue == null || nameValue.isEmpty() || priceValue == null) {
                Notification.show("Please fill in all fields.");
                return;
            }
            DevsApp newApp = new DevsApp(nameValue, "In Review", descValue, priceValue, 0, 0); // <-- USE VALUE
            MockData.addDevsApp(newApp);
            grid.setItems(MockData.getDevsApps());
            Notification.show("'" + nameValue + "' submitted for review!");
            dialog.close();
            appName.clear();
            description.clear();
            price.clear();
        });
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.getFooter().add(cancelButton, submitButton);
        return dialog;
    }
}