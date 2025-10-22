package com.myappstore.components;

import com.myappstore.data.MockData;
import com.myappstore.data.Transaction;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.text.NumberFormat;
import java.util.Locale;

public class EarningsTabContent extends VerticalLayout {

    // Keep references to components that need updating
    private H2 balanceAmount;
    private Grid<Transaction> grid;
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    public EarningsTabContent() {
        setPadding(false);
        addClassName("earnings-tab");

        Dialog confirmationDialog = createConfirmationDialog();

        Div balanceDisplay = new Div();
        balanceDisplay.addClassName("balance-display");
        Span balanceLabel = new Span("Current Balance");
        balanceAmount = new H2(currencyFormatter.format(MockData.getCurrentBalance()));
        
        Button payoutButton = new Button("Request Payout");
        payoutButton.addClassName("payout-button");
        payoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        payoutButton.addClickListener(event -> confirmationDialog.open());
        balanceDisplay.add(balanceLabel, balanceAmount, payoutButton);

        H3 historyTitle = new H3("Recent Transactions");
        grid = new Grid<>(Transaction.class, false);
        grid.addClassName("earnings-grid");
        grid.addColumn(Transaction::getDate).setHeader("Date").setSortable(true);
        grid.addColumn(Transaction::getDescription).setHeader("Description");
        grid.addColumn(t -> currencyFormatter.format(t.getAmount())).setHeader("Amount");
        grid.setItems(MockData.getTransactions());

        add(balanceDisplay, historyTitle, grid);
    }

    private Dialog createConfirmationDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Confirm Payout");
        dialog.add(new Paragraph("Are you sure you want to request a payout of " + currencyFormatter.format(MockData.getCurrentBalance()) + "?"));

        Button confirmButton = new Button("Confirm", event -> {
            MockData.processPayout(); // Call the logic in our data source
            refreshData(); // Refresh the UI on this page
            Notification.show("Payout requested successfully!", 3000, Notification.Position.BOTTOM_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            dialog.close();
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.getFooter().add(cancelButton, confirmButton);
        return dialog;
    }

    // New method to refresh the UI with the latest data
    private void refreshData() {
        balanceAmount.setText(currencyFormatter.format(MockData.getCurrentBalance()));
        grid.setItems(MockData.getTransactions());
    }
}