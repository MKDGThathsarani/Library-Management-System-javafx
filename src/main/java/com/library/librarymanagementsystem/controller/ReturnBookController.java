package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.BookDAO;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
        returnDatePicker.setValue(LocalDate.now());

        // Database එකෙන් පොත් ටික ලෝඩ් කරන්න
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.getAllBooks();

        borrowedBookCombo.getItems().clear(); // පරණ දේවල් අයින් කරන්න
        for (Book b : books) {
            borrowedBookCombo.getItems().add(b.getBookId() + " - " + b.getTitle());
        }
    }

    @FXML
    public void handleLoad() {
        if (borrowedBookCombo.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please select a book first.");
            return;
        }


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

        // ඔබට මේකත් Database එකට සම්බන්ධ කරන්න පුළුවන්.
        // දැනට අපි ඒක කරන්නේ නෑ.

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