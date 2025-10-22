package com.myappstore.components;

import com.myappstore.data.DevPost;
import com.myappstore.data.MockData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import java.time.LocalDate;

public class PostsTabContent extends VerticalLayout {

    private final Div postEditor;
    private final Grid<DevPost> postGrid;
    private final Button newPostButton;

    public PostsTabContent() {
        setPadding(false);
        addClassName("posts-tab");

        newPostButton = new Button("New Post", VaadinIcon.PLUS.create());
        newPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        postGrid = createPostGrid();
        postEditor = createPostEditor();

        newPostButton.addClickListener(event -> showPostEditor());

        add(newPostButton, postGrid, postEditor);
    }
    
    private Grid<DevPost> createPostGrid() {
        Grid<DevPost> grid = new Grid<>(DevPost.class, false);
        grid.addClassName("posts-grid");
        grid.addColumn(DevPost::getTitle).setHeader("Title");
        grid.addColumn(DevPost::getStatus).setHeader("Status");
        grid.addColumn(DevPost::getPublishedDate).setHeader("Published");
        grid.addColumn(DevPost::getViews).setHeader("Views");
        grid.addColumn(DevPost::getLikes).setHeader("Likes");
        grid.setItems(MockData.getDevsPosts());
        return grid;
    }

    private Div createPostEditor() {
        Div editor = new Div();
        editor.addClassName("post-editor");
        editor.setVisible(false);

        H3 editorTitle = new H3("Create New Post");
        TextField postTitle = new TextField("Post Title");
        TextArea postContent = new TextArea("Content (supports Markdown)");
        postContent.setHeight("200px");

        Button publishButton = new Button("Publish Post", event -> {
            String title = postTitle.getValue();
            if (title.isEmpty()) {
                Notification.show("Please enter a title.");
                return;
            }
            DevPost newPost = new DevPost(title, "Published", LocalDate.now(), 0, 0);
            MockData.addPost(newPost); // Add the post to our mock data
            postGrid.setItems(MockData.getDevsPosts()); // Refresh the grid
            hidePostEditor(postTitle, postContent);
            Notification.show("Post published successfully!");
        });
        publishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancel", event -> hidePostEditor(postTitle, postContent));

        editor.add(editorTitle, postTitle, postContent, publishButton, cancelButton);
        return editor;
    }

    private void showPostEditor() {
        newPostButton.setVisible(false);
        postGrid.setVisible(false);
        postEditor.setVisible(true);
    }

    private void hidePostEditor(TextField title, TextArea content) {
        postEditor.setVisible(false);
        newPostButton.setVisible(true);
        postGrid.setVisible(true);
        title.clear();
        content.clear();
    }
}