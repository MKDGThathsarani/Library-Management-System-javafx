package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.BorrowDAO;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class IssueBookController implements Initializable {

    @FXML private ComboBox<String> memberCombo;
    @FXML private ComboBox<String> bookCombo;
    @FXML private DatePicker issueDatePicker;
    @FXML private DatePicker dueDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberCombo.setItems(FXCollections.observableArrayList("M001 - Kamal", "M002 - Nimal", "M003 - Sara"));
        bookCombo.setItems(FXCollections.observableArrayList("B001 - Java Basics", "B002 - DBMS", "B003 - Networks"));
        issueDatePicker.setValue(LocalDate.now());
        dueDatePicker.setValue(LocalDate.now().plusDays(14));
    }

    @FXML
    public void handleIssue() {
        if (memberCombo.getValue() == null || bookCombo.getValue() == null
                || issueDatePicker.getValue() == null || dueDatePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please fill all fields.");
            return;
        }
        if (dueDatePicker.getValue().isBefore(issueDatePicker.getValue())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Date", "Due date must be after issue date.");
            return;
        }
        String memberId = memberCombo.getValue().split(" ")[0];
        String bookId = bookCombo.getValue().split(" ")[0];

        BorrowDAO dao = new BorrowDAO();
        boolean success = dao.issueBook(memberId, bookId,
                issueDatePicker.getValue().toString(),
                dueDatePicker.getValue().toString());

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book issued successfully!");
            handleClear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to issue book.");
        }
    }

    @FXML
    public void handleClear() {
        memberCombo.setValue(null);
        bookCombo.setValue(null);
        issueDatePicker.setValue(null);
        dueDatePicker.setValue(null);
    }

    @FXML
    public void goBack() { NavigationUtil.navigateTo("dashboard.fxml", "Dashboard", 1100, 700); }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}