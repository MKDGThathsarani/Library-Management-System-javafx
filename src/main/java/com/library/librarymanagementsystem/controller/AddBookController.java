package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @FXML private TextField bookIdField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextField yearField;
    @FXML private TextField quantityField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryCombo.setItems(FXCollections.observableArrayList(
                "Fiction", "Non-Fiction", "Science", "Technology", "History", "Children", "Other"
        ));
    }

    @FXML
    public void handleAddBook() {
        if (bookIdField.getText().isEmpty() || titleField.getText().isEmpty()
                || authorField.getText().isEmpty() || categoryCombo.getValue() == null
                || yearField.getText().isEmpty() || quantityField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please fill all fields.");
            return;
        }

        try {
            int year = Integer.parseInt(yearField.getText().trim());
            int qty = Integer.parseInt(quantityField.getText().trim());
            if (year < 1000 || year > 2100 || qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Year and Quantity must be valid numbers.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
        handleClear();
    }

    @FXML
    public void handleClear() {
        bookIdField.clear();
        titleField.clear();
        authorField.clear();
        categoryCombo.setValue(null);
        yearField.clear();
        quantityField.clear();
    }

    @FXML
    public void goBack() {
        NavigationUtil.navigateTo("dashboard.fxml", "Dashboard", 1100, 700);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}