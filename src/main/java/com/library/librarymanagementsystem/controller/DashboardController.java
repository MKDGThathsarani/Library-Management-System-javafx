package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.util.DatabaseUtil;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController {

    @FXML private Label totalBooksLabel, totalMembersLabel, borrowedLabel, overdueLabel;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        loadStats();
    }

    private void loadStats() {
        try (java.sql.Connection conn = DatabaseUtil.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {

            java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM books");
            if(rs.next()) totalBooksLabel.setText(String.valueOf(rs.getInt(1)));

            rs = stmt.executeQuery("SELECT COUNT(*) FROM members");
            if(rs.next()) totalMembersLabel.setText(String.valueOf(rs.getInt(1)));

            rs = stmt.executeQuery("SELECT COUNT(*) FROM borrow_records WHERE status = 'Borrowed'");
            if(rs.next()) borrowedLabel.setText(String.valueOf(rs.getInt(1)));

            rs = stmt.executeQuery("SELECT COUNT(*) FROM borrow_records WHERE status = 'Overdue'");
            if(rs.next()) overdueLabel.setText(String.valueOf(rs.getInt(1)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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