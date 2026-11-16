package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.BorrowDAO;
import com.library.librarymanagementsystem.dto.BorrowRecordDTO; // DTO එක import කළා
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

    // <BorrowRecord> වෙනුවට <BorrowRecordDTO> දාන්න
    @FXML private TableView<BorrowRecordDTO> historyTable;
    @FXML private TableColumn<BorrowRecordDTO, String> colMemberId;
    @FXML private TableColumn<BorrowRecordDTO, String> colBookTitle;
    @FXML private TableColumn<BorrowRecordDTO, String> colIssueDate;
    @FXML private TableColumn<BorrowRecordDTO, String> colDueDate;
    @FXML private TableColumn<BorrowRecordDTO, String> colReturnDate;
    @FXML private TableColumn<BorrowRecordDTO, String> colStatus;
    @FXML private TextField searchField;

    // ObservableList එකත් BorrowRecordDTO කරන්න
    private final ObservableList<BorrowRecordDTO> records = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadHistory(); // Database එකෙන් load කරන method එක call කරන්න
    }

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

        // Filtered list එකත් BorrowRecordDTO කරන්න
        ObservableList<BorrowRecordDTO> filtered = FXCollections.observableArrayList();
        for (BorrowRecordDTO r : records) {
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