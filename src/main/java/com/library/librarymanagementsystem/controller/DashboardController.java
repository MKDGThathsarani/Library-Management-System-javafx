package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DashboardController {

    @FXML public void goAddBook() { NavigationUtil.navigateTo("add-book.fxml", "Add Book", 900, 650); }
    @FXML public void goAddMember() { NavigationUtil.navigateTo("add-member.fxml", "Add Member", 900, 650); }
    @FXML public void goViewMembers() { NavigationUtil.navigateTo("view-members.fxml", "View Members", 1100, 700); }
    @FXML public void goIssueBook() { NavigationUtil.navigateTo("issue-book.fxml", "Issue Book", 900, 650); }
    @FXML public void goReturnBook() { NavigationUtil.navigateTo("return-book.fxml", "Return Book", 900, 650); }
    @FXML public void goHistory() { NavigationUtil.navigateTo("history.fxml", "Borrowing History", 1100, 700); }
    @FXML public void goDashboard() { NavigationUtil.navigateTo("dashboard.fxml", "Dashboard", 1100, 700); }

    @FXML
    public void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NavigationUtil.navigateTo("login.fxml", "Login", 900, 600);
        }
    }
}