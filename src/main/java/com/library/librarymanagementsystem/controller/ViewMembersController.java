package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.MemberDAO;
import com.library.librarymanagementsystem.dto.MemberDTO; // Model එක වෙනුවට DTO එක
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

    // TableView සහ TableColumn වල <Member> වෙනුවට <MemberDTO> දාන්න
    @FXML private TableView<MemberDTO> membersTable;
    @FXML private TableColumn<MemberDTO, String> colId;
    @FXML private TableColumn<MemberDTO, String> colName;
    @FXML private TableColumn<MemberDTO, String> colEmail;
    @FXML private TableColumn<MemberDTO, String> colPhone;
    @FXML private TableColumn<MemberDTO, String> colAddress;
    @FXML private TableColumn<MemberDTO, String> colAction;
    @FXML private TextField searchField;

    // ObservableList එකත් MemberDTO කරන්න
    private final ObservableList<MemberDTO> members = FXCollections.observableArrayList();

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
        // Filtered List එකත් MemberDTO කරන්න
        ObservableList<MemberDTO> filtered = FXCollections.observableArrayList();
        for (MemberDTO m : members) {
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
        // Selected object එක MemberDTO කරන්න
        MemberDTO selected = membersTable.getSelectionModel().getSelectedItem();
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

            // MemberDTO එකේ නම වෙනස් කරන්න setter එකක් නැති නිසා අලුත් DTO එකක් හදනවා
            MemberDTO updatedMember = new MemberDTO(
                    selected.getMemberId(),
                    result.get().trim(), // අලුත් නම
                    selected.getEmail(),
                    selected.getPhone(),
                    selected.getAddress()
            );

            MemberDAO memberDAO = new MemberDAO();
            boolean success = memberDAO.updateMember(updatedMember);

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
        // Selected object එක MemberDTO කරන්න
        MemberDTO selected = membersTable.getSelectionModel().getSelectedItem();
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