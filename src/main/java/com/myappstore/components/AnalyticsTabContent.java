package com.myappstore.components;

import com.myappstore.data.MockData;
import com.vaadin.flow.component.Html; // <-- CORRECTED IMPORT
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnalyticsTabContent extends VerticalLayout {

    private final Div chartContainer = new Div();

    public AnalyticsTabContent() {
        setPadding(false);
        setSizeFull();
        addClassName("analytics-tab");

        HorizontalLayout filterBar = createFilterBar();

        Div chartWrapper = new Div();
        chartWrapper.addClassName("chart-wrapper");
        H3 chartTitle = new H3("Views");
        chartContainer.addClassName("chart-container");
        chartWrapper.add(chartTitle, chartContainer);

        add(filterBar, chartWrapper);
        
        updateChart();
    }

    private HorizontalLayout createFilterBar() {
        HorizontalLayout filterBar = new HorizontalLayout();
        filterBar.addClassName("analytics-filters");
        filterBar.setAlignItems(FlexComponent.Alignment.BASELINE);

        Select<String> appSelect = new Select<>();
        appSelect.setLabel("Project");
        List<String> appNames = MockData.getDevsApps().stream().map(app -> app.getName()).collect(Collectors.toList());
        appNames.add(0, "All Projects");
        appSelect.setItems(appNames);
        appSelect.setValue("All Projects");
        
        Select<String> timeRange = new Select<>();
        timeRange.setItems("Last 7 days", "Last 30 days", "Last 90 days");
        timeRange.setValue("Last 30 days");

        DatePicker fromDate = new DatePicker("From");
        DatePicker toDate = new DatePicker("To");
        fromDate.setValue(LocalDate.now().minusDays(30));
        toDate.setValue(LocalDate.now());
        
        appSelect.addValueChangeListener(event -> updateChart());
        timeRange.addValueChangeListener(event -> {
            int days = Integer.parseInt(event.getValue().split(" ")[1]);
            fromDate.setValue(LocalDate.now().minusDays(days));
            toDate.setValue(LocalDate.now());
            updateChart();
        });
        fromDate.addValueChangeListener(event -> updateChart());
        toDate.addValueChangeListener(event -> updateChart());

        filterBar.add(appSelect, timeRange, fromDate, toDate);
        return filterBar;
    }
    
    private void updateChart() {
        chartContainer.removeAll();
        Random random = new Random();
        List<String> points = new ArrayList<>();
        
        int numDataPoints = 30;
        for (int i = 0; i < numDataPoints; i++) {
            double x = (i / (double)(numDataPoints - 1)) * 100;
            double y = random.nextInt(10, 80);
            
            Div dataPoint = new Div();
            dataPoint.addClassName("data-point");
            dataPoint.getStyle().set("left", x + "%");
            dataPoint.getStyle().set("bottom", y + "%");
            
            Span tooltip = new Span((random.nextInt(50, 500)) + " views");
            tooltip.addClassName("tooltip");
            dataPoint.add(tooltip);

            chartContainer.add(dataPoint);
            points.add(x + "," + (100 - y));
        }
        
        // --- THIS IS THE CORRECTED CODE ---
        // We create an Html component to render our raw SVG string
        String pathData = "M " + String.join(" L ", points);
        Html line = new Html("<svg width='100%' height='100%' xmlns='http://www.w3.org/2000/svg'><path d='" + pathData + "' class='chart-line' /></svg>");
        chartContainer.add(line);
    }
}