package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.util.DatabaseUtil;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Empty check
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("⚠ Please fill in all fields.");
            return;
        }

        // Database එකෙන් check කරන්න
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login සාර්ථකයි
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Success");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + username + "!");
                alert.showAndWait();
                NavigationUtil.navigateTo("dashboard.fxml", "Dashboard", 1100, 700);
            } else {
                // Login අසාර්ථකයි
                errorLabel.setText("❌ Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("❌ Database error. Please try again.");
        }
    }

    @FXML
    public void handleClear() {
        usernameField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
}