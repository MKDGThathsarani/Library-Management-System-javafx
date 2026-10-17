package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.MemberDAO;
import com.library.librarymanagementsystem.model.Member;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewMembersController implements Initializable {

    @FXML private TableView<Member> membersTable;
    @FXML private TableColumn<Member, String> colId;
    @FXML private TableColumn<Member, String> colName;
    @FXML private TableColumn<Member, String> colEmail;
    @FXML private TableColumn<Member, String> colPhone;
    @FXML private TableColumn<Member, String> colAddress;
    @FXML private TableColumn<Member, String> colAction;
    @FXML private TextField searchField;

    private final ObservableList<Member> members = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("memberId"));

        // Database එකෙන් members load කරන්න
        loadMembers();
    }

    // Database එකෙන් members load කරන method එක
    private void loadMembers() {
        MemberDAO memberDAO = new MemberDAO();
        members.clear();
        members.addAll(memberDAO.getAllMembers());
        membersTable.setItems(members);
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            membersTable.setItems(members);
            return;
        }
        ObservableList<Member> filtered = FXCollections.observableArrayList();
        for (Member m : members) {
            if (m.getName().toLowerCase().contains(query)
                    || m.getMemberId().toLowerCase().contains(query)
                    || m.getEmail().toLowerCase().contains(query)) {
                filtered.add(m);
            }
        }
        membersTable.setItems(filtered);
    }

    @FXML
    public void handleEdit() {
        Member selected = membersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a member to edit.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog(selected.getName());
        dialog.setTitle("Edit Member");
        dialog.setHeaderText("Editing: " + selected.getMemberId());
        dialog.setContentText("Enter new Full Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            selected.setName(result.get().trim());

            MemberDAO memberDAO = new MemberDAO();
            boolean success = memberDAO.updateMember(selected);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Member updated successfully!");
                loadMembers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update member.");
            }
        }
    }

    @FXML
    public void handleDelete() {
        Member selected = membersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a member to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete member " + selected.getName() + "?");

        Optional<ButtonType> r = confirm.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            // Database එකෙන් delete කරන්න
            MemberDAO memberDAO = new MemberDAO();
            boolean success = memberDAO.deleteMember(selected.getMemberId());

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Member deleted successfully!");
                loadMembers(); // Table එක refresh කරන්න
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete member.");
            }
        }
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