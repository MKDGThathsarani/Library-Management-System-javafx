package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReturnBookController implements Initializable {

    @FXML private ComboBox<String> borrowedBookCombo;
    @FXML private Label memberIdLabel;
    @FXML private Label memberNameLabel;
    @FXML private Label bookTitleLabel;
    @FXML private Label borrowedDateLabel;
    @FXML private Label dueDateLabel;
    @FXML private DatePicker returnDatePicker;
    @FXML private Label overdueStatusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borrowedBookCombo.setItems(FXCollections.observableArrayList(
                "B001 - Java Basics",
                "B002 - DBMS Concepts",
                "B003 - Computer Networks"
        ));
        returnDatePicker.setValue(LocalDate.now());
    }

    @FXML
    public void handleLoad() {
        if (borrowedBookCombo.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please select a book first.");
            return;
        }

        // Sample data (real application එකේ database එකෙන් load කරන්න)
        memberIdLabel.setText("M001");
        memberNameLabel.setText("Kamal Perera");
        bookTitleLabel.setText(borrowedBookCombo.getValue());
        borrowedDateLabel.setText("2024-01-15");
        dueDateLabel.setText("2024-01-29");

        // Overdue status check
        LocalDate dueDate = LocalDate.of(2024, 1, 29);
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            long daysOverdue = today.toEpochDay() - dueDate.toEpochDay();
            overdueStatusLabel.setText("⚠ OVERDUE by " + daysOverdue + " days!");
            overdueStatusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        } else {
            overdueStatusLabel.setText("✅ Not overdue");
            overdueStatusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 14px; -fx-font-weight: bold;");
        }
    }

    @FXML
    public void handleReturn() {
        if (borrowedBookCombo.getValue() == null || returnDatePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please load a book and select return date.");
            return;
        }
        showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully!");
        handleClear();
    }

    @FXML
    public void handleClear() {
        borrowedBookCombo.setValue(null);
        memberIdLabel.setText("-");
        memberNameLabel.setText("-");
        bookTitleLabel.setText("-");
        borrowedDateLabel.setText("-");
        dueDateLabel.setText("-");
        returnDatePicker.setValue(LocalDate.now());
        overdueStatusLabel.setText("");
    }

    @FXML
    public void goBack() {
        NavigationUtil.navigateTo("dashboard.fxml", "Dashboard", 1100, 700);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}