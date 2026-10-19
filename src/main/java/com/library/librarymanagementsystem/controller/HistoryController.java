package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.BorrowDAO;
import com.library.librarymanagementsystem.model.BorrowRecord;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML private TableView<BorrowRecord> historyTable;
    @FXML private TableColumn<BorrowRecord, String> colMemberId;
    @FXML private TableColumn<BorrowRecord, String> colBookTitle;
    @FXML private TableColumn<BorrowRecord, String> colIssueDate;
    @FXML private TableColumn<BorrowRecord, String> colDueDate;
    @FXML private TableColumn<BorrowRecord, String> colReturnDate;
    @FXML private TableColumn<BorrowRecord, String> colStatus;
    @FXML private TextField searchField;

    private final ObservableList<BorrowRecord> records = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadHistory(); // මේක initialize එක ඇතුලේ කතා කරනවා
    }

    // මේ method එක initialize එකට පිටින් (class එක ඇතුලේ) තියෙන්න ඕනේ!
    private void loadHistory() {
        BorrowDAO dao = new BorrowDAO();
        records.clear();
        records.addAll(dao.getAllRecords());
        historyTable.setItems(records);
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            historyTable.setItems(records);
            return;
        }
        ObservableList<BorrowRecord> filtered = FXCollections.observableArrayList();
        for (BorrowRecord r : records) {
            if (r.getMemberId().toLowerCase().contains(query)
                    || r.getBookTitle().toLowerCase().contains(query)) {
                filtered.add(r);
            }
        }
        historyTable.setItems(filtered);
    }

    @FXML
    public void goBack() {
        NavigationUtil.navigateTo("dashboard.fxml", "Dashboard", 1100, 700);
    }
}