package com.myappstore.components;

import com.myappstore.data.MockData;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Sidebar extends Div {

    private Consumer<String> appOfTheDayClickListener;
    private Select<String> categorySelect;
    private RadioButtonGroup<String> priceGroup;
    // These fields are for the clickable star rating filter
    private List<Div> ratingItems = new ArrayList<>();
    private double currentRatingFilter = 0;

    public Sidebar() {
        addClassName("sidebar");
        
        add(createSection("App of the Day", createAppOfTheDay()));
        
        categorySelect = createCategoryFilter();
        categorySelect.addValueChangeListener(event -> fireFilterChangeEvent());
        add(createSection("Categories", categorySelect));

        priceGroup = createPriceFilter();
        priceGroup.addValueChangeListener(event -> fireFilterChangeEvent());
        
        // This creates the interactive star rating list
        Div ratingFilter = createRatingFilter();
        add(createSection("Filters", priceGroup, ratingFilter));
        
        add(createSection("Top Categories", createTopCategoryPills()));
        add(createSection("Store Statistics", createStatsPanel(), createStatBar()));
    }
    
    // Fires an event with all current filter values
    private void fireFilterChangeEvent() {
        fireEvent(new FilterChangeEvent(this, false, categorySelect.getValue(), priceGroup.getValue(), currentRatingFilter));
    }

    // Creates the container for the rating items
    private Div createRatingFilter() {
        Div container = new Div();
        container.getStyle().set("padding-top", "1rem");
        
        Div fourStars = createRatingItem("★★★★☆ & Up", 4.0);
        Div threeStars = createRatingItem("★★★☆☆ & Up", 3.0);
        Div twoStars = createRatingItem("★★☆☆☆ & Up", 2.0);

        ratingItems.addAll(List.of(fourStars, threeStars, twoStars));
        container.add(fourStars, threeStars, twoStars);
        return container;
    }
    
    // Creates a single clickable rating item
    private Div createRatingItem(String text, double ratingValue) {
        Div item = new Div(new Span(text));
        item.addClassName("rating-filter-item");
        item.addClickListener(event -> {
            // If the same filter is clicked again, reset it to 0
            if (currentRatingFilter == ratingValue) {
                currentRatingFilter = 0;
                item.removeClassName("active");
            } else {
                // Otherwise, set the new filter value
                currentRatingFilter = ratingValue;
                // Remove 'active' from all other items and add it to the clicked one
                ratingItems.forEach(i -> i.removeClassName("active"));
                item.addClassName("active");
            }
            // Fire the main event to update the grid
            fireFilterChangeEvent();
        });
        return item;
    }
    
    // --- The rest of the file is for other components and events ---

    public void addAppOfTheDayClickListener(Consumer<String> listener) { this.appOfTheDayClickListener = listener; }
    public static class FilterChangeEvent extends ComponentEvent<Sidebar> {
        private final String category; private final String price; private final double rating;
        public FilterChangeEvent(Sidebar source, boolean fromClient, String category, String price, double rating) {
            super(source, fromClient); this.category = category; this.price = price; this.rating = rating;
        }
        public String getCategory() { return category; } public String getPrice() { return price; } public double getRating() { return rating; }
    }
    public Registration addFilterChangeListener(ComponentEventListener<FilterChangeEvent> listener) {
        return addListener(FilterChangeEvent.class, listener);
    }
    private Div createAppOfTheDay() {
        Div card = new Div(); card.addClassName("app-of-the-day"); var featuredApp = MockData.getFeaturedApp();
        H3 title = new H3(featuredApp.getTitle()); Span description = new Span("Top new game this week!");
        card.add(title, description);
        card.addClickListener(event -> {
            if (appOfTheDayClickListener != null) { appOfTheDayClickListener.accept(featuredApp.getTitle()); }
        });
        return card;
    }
    private HorizontalLayout createCategoryPill(VaadinIcon icon, String text) {
        HorizontalLayout pill = new HorizontalLayout(icon.create(), new Span(text)); pill.addClassName("category-pill"); pill.setAlignItems(FlexComponent.Alignment.CENTER);
        pill.addClickListener(event -> { categorySelect.setValue(text); fireFilterChangeEvent(); });
        return pill;
    }
    private Div createSection(String title, com.vaadin.flow.component.Component... components) {
        Div section = new Div(); section.addClassName("sidebar-section"); section.add(new H3(title));
        for (com.vaadin.flow.component.Component component : components) { section.add(component); }
        return section;
    }
    private Select<String> createCategoryFilter() {
        Select<String> select = new Select<>(); select.setLabel("All Categories");
        select.setItems("All", "Productivity", "Games", "Finance", "Music & Audio", "Social", "Utilities");
        select.setValue("All"); return select;
    }
    private Div createTopCategoryPills() {
        Div pillsContainer = new Div(); pillsContainer.addClassName("pills-container");
        pillsContainer.add( createCategoryPill(VaadinIcon.GAMEPAD, "Games"), createCategoryPill(VaadinIcon.CHART, "Productivity"),
            createCategoryPill(VaadinIcon.WALLET, "Finance"), createCategoryPill(VaadinIcon.USERS, "Social"), createCategoryPill(VaadinIcon.TOOLS, "Utilities") );
        return pillsContainer;
    }
    private RadioButtonGroup<String> createPriceFilter() {
        RadioButtonGroup<String> priceGroup = new RadioButtonGroup<>(); priceGroup.setLabel("Price");
        priceGroup.setItems("All", "Free", "Paid"); priceGroup.setValue("All"); return priceGroup;
    }
    private Div createStatBar() {
        Div container = new Div(); container.addClassName("stat-bar-container"); Span label = new Span("Free vs Paid Apps");
        Div bar = new Div(); bar.addClassName("stat-bar"); Div freePortion = new Div(); freePortion.addClassName("free-portion");
        freePortion.setWidth("70%"); bar.add(freePortion); container.add(label, bar); return container;
    }
    private Div createStatsPanel() {
        Div statsPanel = new Div(); statsPanel.addClassName("stats-panel");
        statsPanel.add( createStatItem(VaadinIcon.DESKTOP, "Desktop Apps", "748"), createStatItem(VaadinIcon.MOBILE, "Mobile Apps", "1,209"),
            createStatItem(VaadinIcon.GLOBE, "Web Apps", "312") );
        return statsPanel;
    }
    private HorizontalLayout createStatItem(VaadinIcon icon, String label, String value) {
        HorizontalLayout item = new HorizontalLayout(); item.addClassName("stat-item");
        Span labelSpan = new Span(label); Span valueSpan = new Span(value); valueSpan.addClassName("stat-value");
        item.add(icon.create(), labelSpan, valueSpan); item.setAlignItems(FlexComponent.Alignment.CENTER); return item;
    }
}