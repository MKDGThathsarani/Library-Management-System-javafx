package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.MemberDAO;
import com.library.librarymanagementsystem.dto.MemberDTO; // Model එක වෙනුවට DTO එක import කළා
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddMemberController {

    @FXML private TextField memberIdField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressField;

    @FXML
    public void handleRegister() {
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (memberIdField.getText().isEmpty() || nameField.getText().isEmpty()
                || email.isEmpty() || phone.isEmpty() || addressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "All fields are required.");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!phone.matches("\\d{10}")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Phone", "Phone must be 10 digits.");
            return;
        }

        // Member object එක වෙනුවට MemberDTO එකක් හදනවා
        MemberDTO member = new MemberDTO(
                memberIdField.getText().trim(),
                nameField.getText().trim(),
                email,
                phone,
                addressField.getText().trim()
        );

        // Save to database
        MemberDAO memberDAO = new MemberDAO();
        boolean success = memberDAO.addMember(member); // දැන් MemberDTO එක pass කරනවා

        if (success){
            showAlert(Alert.AlertType.INFORMATION,"Success", "Member registered successfully!");
            handleClear();
        }else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register member. Member ID may already exist.");
        }
    }

    @FXML
    public void handleClear() {
        memberIdField.clear();
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
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