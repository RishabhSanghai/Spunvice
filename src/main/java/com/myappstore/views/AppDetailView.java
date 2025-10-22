package com.myappstore.views;

import com.myappstore.data.App;
import com.myappstore.data.MockData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("app-detail")
public class AppDetailView extends VerticalLayout implements HasUrlParameter<String> {

    private String appTitle;

    public AppDetailView() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        addClassName("app-detail-view");
    }

    @Override
    public void setParameter(BeforeEvent event, String appTitle) {
        this.appTitle = appTitle;
        createAppDetailView();
    }

    private void createAppDetailView() {
        // Find the app by title
        App app = MockData.getAllApps().stream()
                .filter(a -> a.getTitle().equals(appTitle))
                .findFirst()
                .orElse(null);

        if (app == null) {
            showAppNotFound();
            return;
        }

        // Header with back button
        HorizontalLayout header = createHeader();
        
        // Main content in a scrollable container
        Div contentWrapper = new Div();
        contentWrapper.addClassName("app-detail-content");
        contentWrapper.getStyle().set("overflow-y", "auto");
        contentWrapper.getStyle().set("height", "calc(100vh - 80px)");
        
        // App header section
        Div appHeader = createAppHeader(app);
        
        // Screenshots section
        Div screenshots = createScreenshotsSection();
        
        // Description and details
        Div detailsSection = createDetailsSection(app);
        
        // Features section
        Div featuresSection = createFeaturesSection(app);
        
        // System Requirements
        Div requirementsSection = createRequirementsSection(app);
        
        // What's New section
        Div whatsNewSection = createWhatsNewSection(app);
        
        // Reviews section
        Div reviewsSection = createReviewsSection();
        
        // Additional Information
        Div additionalInfoSection = createAdditionalInfoSection(app);
        
        contentWrapper.add(appHeader, screenshots, detailsSection, featuresSection, 
                          requirementsSection, whatsNewSection, reviewsSection, additionalInfoSection);
        add(header, contentWrapper);
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("app-detail-header");
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);

        // Back button and store link
        HorizontalLayout leftSection = new HorizontalLayout();
        leftSection.setAlignItems(FlexComponent.Alignment.CENTER);
        
        Button backButton = new Button(VaadinIcon.ARROW_LEFT.create(), event -> {
            UI.getCurrent().getPage().getHistory().back();
        });
        backButton.addClassName("back-button");
        
        RouterLink storeLink = new RouterLink("Spunvice", StorefrontView.class);
        storeLink.addClassName("store-link");
        
        leftSection.add(backButton, storeLink);

        header.add(leftSection);
        return header;
    }

    private Div createAppHeader(App app) {
        Div header = new Div();
        header.addClassName("app-header");
        
        HorizontalLayout mainInfo = new HorizontalLayout();
        mainInfo.addClassName("app-main-info");
        mainInfo.setAlignItems(FlexComponent.Alignment.START);

        // App icon and basic info
        VerticalLayout iconSection = new VerticalLayout();
        iconSection.addClassName("app-icon-section");
        
        Image appIcon = new Image(app.getImageUrl(), app.getTitle());
        appIcon.addClassName("app-detail-icon");
        
        iconSection.add(appIcon);

        // App details
        VerticalLayout infoSection = new VerticalLayout();
        infoSection.addClassName("app-info-section");
        
        H1 appTitle = new H1(app.getTitle());
        appTitle.addClassName("app-detail-title");
        
        Span category = new Span(app.getCategory());
        category.addClassName("app-category");
        
        // Rating
        HorizontalLayout ratingSection = new HorizontalLayout();
        ratingSection.addClassName("rating-section");
        
        Div stars = createStars(Double.parseDouble(app.getRating()));
        Span ratingText = new Span(app.getRating() + " ★ • 12.4K Ratings");
        ratingText.addClassName("rating-text");
        
        Span ageRating = new Span("4+");
        ageRating.addClassName("age-rating");
        
        ratingSection.add(stars, ratingText, ageRating);

        infoSection.add(appTitle, category, ratingSection);

        // Install section
        VerticalLayout installSection = new VerticalLayout();
        installSection.addClassName("install-section");
        
        Span price = new Span(app.getPrice() == 0.0 ? "FREE" : String.format("$%.2f", app.getPrice()));
        price.addClassName("app-price");
        
        Button installButton = new Button("Install", VaadinIcon.DOWNLOAD.create());
        installButton.addClassName("install-button");
        installButton.addClickListener(event -> {
            showInstallationDialog(app);
        });
        
        Span sizeInfo = new Span("156 MB");
        sizeInfo.addClassName("size-info");
        
        installSection.add(price, installButton, sizeInfo);

        mainInfo.add(iconSection, infoSection, installSection);
        header.add(mainInfo);
        
        return header;
    }

    private Div createStars(double rating) {
        Div stars = new Div();
        stars.addClassName("star-rating");
        
        int fullStars = (int) rating;
        boolean hasHalfStar = rating - fullStars >= 0.5;
        
        for (int i = 0; i < 5; i++) {
            Span star = new Span("★");
            star.addClassName("star");
            
            if (i < fullStars) {
                star.addClassName("filled");
            } else if (i == fullStars && hasHalfStar) {
                star.addClassName("half-filled");
            }
            
            stars.add(star);
        }
        
        return stars;
    }

    private Div createScreenshotsSection() {
        Div section = new Div();
        section.addClassName("screenshots-section");
        
        H3 sectionTitle = new H3("Screenshots");
        sectionTitle.addClassName("section-title");
        
        Div screenshotGrid = new Div();
        screenshotGrid.addClassName("screenshot-grid");
        
        // Placeholder screenshots
        for (int i = 1; i <= 5; i++) {
            Image screenshot = new Image(
                "https://picsum.photos/300/600?random=" + i,
                "App Screenshot " + i
            );
            screenshot.addClassName("screenshot");
            screenshotGrid.add(screenshot);
        }
        
        section.add(sectionTitle, screenshotGrid);
        return section;
    }

    private Div createDetailsSection(App app) {
        Div section = new Div();
        section.addClassName("details-section");
        
        H3 descriptionTitle = new H3("Description");
        descriptionTitle.addClassName("section-title");
        
        Paragraph description = new Paragraph(app.getDescription());
        description.addClassName("app-description");
        
        // Additional details
        Div detailsGrid = new Div();
        detailsGrid.addClassName("details-grid");
        
        detailsGrid.add(
            createDetailItem("Category", app.getCategory()),
            createDetailItem("Last Updated", "October 15, 2024"),
            createDetailItem("Version", "2.1.0"),
            createDetailItem("Size", "156 MB"),
            createDetailItem("Developer", "Creative Studios Inc."),
            createDetailItem("Compatibility", "Windows 10+, macOS 11+, Linux"),
            createDetailItem("Languages", "English, Spanish, French, German, Japanese"),
            createDetailItem("Age Rating", "4+")
        );
        
        section.add(descriptionTitle, description, detailsGrid);
        return section;
    }

    private Div createFeaturesSection(App app) {
        Div section = new Div();
        section.addClassName("features-section");
        
        H3 sectionTitle = new H3("Key Features");
        sectionTitle.addClassName("section-title");
        
        UnorderedList featuresList = new UnorderedList();
        featuresList.addClassName("features-list");
        
        // App-specific features based on category
        if (app.getCategory().equals("Games")) {
            featuresList.add(
                createFeatureItem("Immersive gameplay with stunning 3D graphics"),
                createFeatureItem("Multiple game modes and difficulty levels"),
                createFeatureItem("Online multiplayer support"),
                createFeatureItem("Regular content updates and events"),
                createFeatureItem("Controller and keyboard support"),
                createFeatureItem("Cloud save synchronization")
            );
        } else if (app.getCategory().equals("Productivity")) {
            featuresList.add(
                createFeatureItem("Intuitive user interface designed for efficiency"),
                createFeatureItem("Real-time collaboration features"),
                createFeatureItem("Cross-platform synchronization"),
                createFeatureItem("Advanced search and organization"),
                createFeatureItem("Customizable workflows and templates"),
                createFeatureItem("Integration with popular cloud services")
            );
        } else if (app.getCategory().equals("Finance")) {
            featuresList.add(
                createFeatureItem("Bank-level security with 256-bit encryption"),
                createFeatureItem("Automatic transaction categorization"),
                createFeatureItem("Investment tracking and portfolio management"),
                createFeatureItem("Bill payment reminders and scheduling"),
                createFeatureItem("Tax reporting and export capabilities"),
                createFeatureItem("Multi-currency support")
            );
        } else {
            featuresList.add(
                createFeatureItem("Professional-grade tools and filters"),
                createFeatureItem("High-performance processing engine"),
                createFeatureItem("Customizable interface and shortcuts"),
                createFeatureItem("Regular updates with new features"),
                createFeatureItem("Comprehensive documentation and support"),
                createFeatureItem("Community plugins and extensions")
            );
        }
        
        section.add(sectionTitle, featuresList);
        return section;
    }

    private Div createRequirementsSection(App app) {
        Div section = new Div();
        section.addClassName("requirements-section");
        
        H3 sectionTitle = new H3("System Requirements");
        sectionTitle.addClassName("section-title");
        
        Div requirementsGrid = new Div();
        requirementsGrid.addClassName("requirements-grid");
        
        requirementsGrid.add(
            createRequirementItem("Operating System", "Windows 10/11, macOS 11+, Ubuntu 18.04+"),
            createRequirementItem("Processor", "Intel i5 or AMD equivalent (2.0 GHz+)"),
            createRequirementItem("Memory", "8 GB RAM (16 GB recommended)"),
            createRequirementItem("Storage", "500 MB available space"),
            createRequirementItem("Graphics", "DirectX 11 compatible GPU"),
            createRequirementItem("Internet", "Broadband connection for online features")
        );
        
        section.add(sectionTitle, requirementsGrid);
        return section;
    }

    private Div createWhatsNewSection(App app) {
        Div section = new Div();
        section.addClassName("whats-new-section");
        
        H3 sectionTitle = new H3("What's New in Version 2.1.0");
        sectionTitle.addClassName("section-title");
        
        UnorderedList updatesList = new UnorderedList();
        updatesList.addClassName("updates-list");
        
        updatesList.add(
            createUpdateItem("Added dark mode support with customizable themes"),
            createUpdateItem("Improved performance with 40% faster loading times"),
            createUpdateItem("New AI-powered suggestions and recommendations"),
            createUpdateItem("Enhanced security with two-factor authentication"),
            createUpdateItem("Added support for 5 new languages"),
            createUpdateItem("Fixed various bugs and stability issues")
        );
        
        Span updateDate = new Span("Released October 15, 2024");
        updateDate.addClassName("update-date");
        
        section.add(sectionTitle, updatesList, updateDate);
        return section;
    }

    private Div createReviewsSection() {
        Div section = new Div();
        section.addClassName("reviews-section");
        
        H3 sectionTitle = new H3("User Reviews");
        sectionTitle.addClassName("section-title");
        
        // Review summary
        Div reviewSummary = new Div();
        reviewSummary.addClassName("review-summary");
        
        Div overallRating = new Div();
        overallRating.addClassName("overall-rating");
        overallRating.add(new Span("4.8"), new Span("out of 5"));
        
        Div ratingBreakdown = new Div();
        ratingBreakdown.addClassName("rating-breakdown");
        ratingBreakdown.add(
            createRatingBar(5, 78),
            createRatingBar(4, 15),
            createRatingBar(3, 5),
            createRatingBar(2, 1),
            createRatingBar(1, 1)
        );
        
        reviewSummary.add(overallRating, ratingBreakdown);
        
        // Individual reviews
        Div reviewsList = new Div();
        reviewsList.addClassName("reviews-list");
        
        // Sample reviews
        reviewsList.add(
            createReview("Sarah M.", 5, "This app has completely transformed how I work! The interface is intuitive and the features are exactly what I needed. Highly recommended!", "2 days ago"),
            createReview("Alex K.", 4, "Great functionality and regular updates. The customer support is responsive and helpful. Would love to see more customization options.", "1 week ago"),
            createReview("Mike T.", 5, "Worth every penny! The performance improvements in the latest update are noticeable. This has become my go-to app for daily tasks.", "3 weeks ago")
        );
        
        Button seeAllReviews = new Button("See All 124 Reviews", event -> {
            // Would navigate to full reviews page
        });
        seeAllReviews.addClassName("reviews-button");
        
        section.add(sectionTitle, reviewSummary, reviewsList, seeAllReviews);
        return section;
    }

    private Div createAdditionalInfoSection(App app) {
        Div section = new Div();
        section.addClassName("additional-info-section");
        
        H3 sectionTitle = new H3("Additional Information");
        sectionTitle.addClassName("section-title");
        
        Div infoGrid = new Div();
        infoGrid.addClassName("info-grid");
        
        infoGrid.add(
            createInfoItem("Privacy Policy", "View our privacy practices"),
            createInfoItem("Terms of Service", "Read the terms and conditions"),
            createInfoItem("Support", "Contact our support team"),
            createInfoItem("Website", "Visit our official website"),
            createInfoItem("Version History", "See all previous versions"),
            createInfoItem("Report Issue", "Submit bug reports or feedback")
        );
        
        section.add(sectionTitle, infoGrid);
        return section;
    }

    // Helper methods for creating list items and components
    private ListItem createFeatureItem(String text) {
        ListItem item = new ListItem(text);
        item.addClassName("feature-item");
        return item;
    }

    private ListItem createUpdateItem(String text) {
        ListItem item = new ListItem(text);
        item.addClassName("update-item");
        return item;
    }

    private Div createDetailItem(String label, String value) {
        Div item = new Div();
        item.addClassName("detail-item");
        
        Span labelSpan = new Span(label + ":");
        labelSpan.addClassName("detail-label");
        
        Span valueSpan = new Span(value);
        valueSpan.addClassName("detail-value");
        
        item.add(labelSpan, valueSpan);
        return item;
    }

    private Div createRequirementItem(String category, String requirement) {
        Div item = new Div();
        item.addClassName("requirement-item");
        
        Span categorySpan = new Span(category + ":");
        categorySpan.addClassName("requirement-category");
        
        Span requirementSpan = new Span(requirement);
        requirementSpan.addClassName("requirement-value");
        
        item.add(categorySpan, requirementSpan);
        return item;
    }

    private Div createRatingBar(int stars, int percentage) {
        Div bar = new Div();
        bar.addClassName("rating-bar");
        
        Span starLabel = new Span(stars + "★");
        starLabel.addClassName("star-label");
        
        Div barContainer = new Div();
        barContainer.addClassName("bar-container");
        
        Div barFill = new Div();
        barFill.addClassName("bar-fill");
        barFill.getStyle().set("width", percentage + "%");
        
        barContainer.add(barFill);
        
        Span percentageLabel = new Span(percentage + "%");
        percentageLabel.addClassName("percentage-label");
        
        bar.add(starLabel, barContainer, percentageLabel);
        return bar;
    }

    private Div createReview(String reviewer, int rating, String text, String date) {
        Div review = new Div();
        review.addClassName("review");
        
        HorizontalLayout reviewHeader = new HorizontalLayout();
        reviewHeader.addClassName("review-header");
        
        Span reviewerName = new Span(reviewer);
        reviewerName.addClassName("reviewer-name");
        
        Div reviewStars = createStars(rating);
        
        reviewHeader.add(reviewerName, reviewStars);
        
        Paragraph reviewText = new Paragraph(text);
        reviewText.addClassName("review-text");
        
        Span reviewDate = new Span(date);
        reviewDate.addClassName("review-date");
        
        review.add(reviewHeader, reviewText, reviewDate);
        return review;
    }

    private Div createInfoItem(String title, String description) {
        Div item = new Div();
        item.addClassName("info-item");
        
        Span titleSpan = new Span(title);
        titleSpan.addClassName("info-title");
        
        Span descSpan = new Span(description);
        descSpan.addClassName("info-desc");
        
        item.add(titleSpan, descSpan);
        return item;
    }

    private void showInstallationDialog(App app) {
        Notification.show("Installing " + app.getTitle() + "...", 3000, Notification.Position.MIDDLE);
        
        getUI().ifPresent(ui -> {
            ui.setPollInterval(100);
            ui.access(() -> {
                try {
                    Thread.sleep(2000);
                    Notification.show("✓ " + app.getTitle() + " installed successfully!", 3000, Notification.Position.MIDDLE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    ui.setPollInterval(-1);
                }
            });
        });
    }

    private void showAppNotFound() {
        VerticalLayout errorLayout = new VerticalLayout();
        errorLayout.addClassName("error-layout");
        errorLayout.setAlignItems(Alignment.CENTER);
        errorLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        errorLayout.setSizeFull();
        
        H2 errorTitle = new H2("App Not Found");
        Paragraph errorMessage = new Paragraph("The app you're looking for doesn't exist or has been removed.");
        
        Button backButton = new Button("Back to Store", event -> {
            UI.getCurrent().navigate("store");
        });
        backButton.addClassName("back-button");
        
        errorLayout.add(errorTitle, errorMessage, backButton);
        add(errorLayout);
    }
}