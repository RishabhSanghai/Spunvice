package com.myappstore.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private final VerticalLayout loginForm;
    private final VerticalLayout registerForm;
    private final Tabs authTabs;

    public LoginView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("login-view");

        // Main login container
        Div loginContainer = new Div();
        loginContainer.addClassName("login-container");

        // Simple header
        H1 title = new H1("Spunvice");
        title.addClassName("login-title");
        
        Paragraph subtitle = new Paragraph("Your Creative App Marketplace");
        subtitle.addClassName("login-subtitle");

        // Auth tabs (Login/Register)
        authTabs = createAuthTabs();
        
        // Forms
        loginForm = createLoginForm();
        registerForm = createRegisterForm();
        
        // Show login form by default, hide register form
        registerForm.setVisible(false);

        loginContainer.add(title, subtitle, authTabs, loginForm, registerForm);
        add(loginContainer);
    }

    private Tabs createAuthTabs() {
        Tab loginTab = new Tab("Sign In");
        Tab registerTab = new Tab("Create Account");
        
        Tabs tabs = new Tabs(loginTab, registerTab);
        tabs.addClassName("auth-tabs");
        tabs.setSelectedTab(loginTab);
        
        tabs.addSelectedChangeListener(event -> {
            boolean isLogin = event.getSelectedTab().getLabel().equals("Sign In");
            loginForm.setVisible(isLogin);
            registerForm.setVisible(!isLogin);
        });
        
        return tabs;
    }

    private VerticalLayout createLoginForm() {
        VerticalLayout form = new VerticalLayout();
        form.addClassName("login-form");
        form.setPadding(false);
        form.setSpacing(true);

        TextField emailField = new TextField("Email or Username");
        emailField.addClassName("login-field");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("Password");
        passwordField.addClassName("login-field");
        passwordField.setWidthFull();

        Button loginButton = new Button("Sign In", event -> {
            if (!emailField.getValue().isEmpty() && !passwordField.getValue().isEmpty()) {
                // Dummy login - just check if fields are filled
                Notification.show("Welcome to Spunvice! 🚀");
                getUI().ifPresent(ui -> ui.navigate("store"));
            } else {
                emailField.setInvalid(true);
                passwordField.setInvalid(true);
                Notification.show("Please enter both email and password");
            }
        });
        loginButton.addClassName("login-button");
        loginButton.setWidthFull();

        form.add(emailField, passwordField, loginButton);
        return form;
    }

    private VerticalLayout createRegisterForm() {
        VerticalLayout form = new VerticalLayout();
        form.addClassName("register-form");
        form.setPadding(false);
        form.setSpacing(true);

        TextField fullNameField = new TextField("Full Name");
        fullNameField.addClassName("login-field");
        fullNameField.setWidthFull();

        EmailField emailField = new EmailField("Email");
        emailField.addClassName("login-field");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("Password");
        passwordField.addClassName("login-field");
        passwordField.setWidthFull();

        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
        confirmPasswordField.addClassName("login-field");
        confirmPasswordField.setWidthFull();

        Button registerButton = new Button("Create Account", event -> {
            if (fullNameField.getValue().isEmpty() || 
                emailField.getValue().isEmpty() || 
                passwordField.getValue().isEmpty() ||
                confirmPasswordField.getValue().isEmpty()) {
                
                Notification.show("Please fill in all fields");
                return;
            }

            if (!passwordField.getValue().equals(confirmPasswordField.getValue())) {
                Notification.show("Passwords don't match");
                confirmPasswordField.setInvalid(true);
                return;
            }

            // Dummy registration - just show success message
            Notification.show("Account created successfully! Welcome to Spunvice! 🎉");
            
            // Auto-login and redirect to store
            getUI().ifPresent(ui -> ui.navigate("store"));
        });
        registerButton.addClassName("login-button");
        registerButton.setWidthFull();

        form.add(fullNameField, emailField, passwordField, confirmPasswordField, registerButton);
        return form;
    }
}