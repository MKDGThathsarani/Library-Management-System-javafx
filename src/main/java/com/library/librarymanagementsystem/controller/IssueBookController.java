package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.dao.BorrowDAO;
import com.library.librarymanagementsystem.dao.BookDAO;
import com.library.librarymanagementsystem.dao.MemberDAO;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.model.Member;
import com.library.librarymanagementsystem.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class IssueBookController implements Initializable {

    @FXML private ComboBox<String> memberCombo;
    @FXML private ComboBox<String> bookCombo;
    @FXML private DatePicker issueDatePicker;
    @FXML private DatePicker dueDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // දින වකවානු සෙට් කිරීම
        issueDatePicker.setValue(LocalDate.now());
        dueDatePicker.setValue(LocalDate.now().plusDays(14));

        // Database එකෙන් සාමාජිකයන් (Members) ගෙන්වා ගැනීම
        MemberDAO memberDAO = new MemberDAO();
        List<Member> members = memberDAO.getAllMembers();
        memberCombo.getItems().clear(); // පරණ දේවල් අයින් කිරීම
        for (Member m : members) {
            memberCombo.getItems().add(m.getMemberId() + " - " + m.getName());
        }

        // Database එකෙන් පොත් (Books) ගෙන්වා ගැනීම
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.getAllBooks();
        bookCombo.getItems().clear(); // පරණ දේවල් අයින් කිරීම
        for (Book b : books) {
            bookCombo.getItems().add(b.getBookId() + " - " + b.getTitle());
        }
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

        // ComboBox එකේ තියෙන අගයෙන් ID එක විතරක් වෙන් කර ගැනීම (උදා: "M001 - Kamal" -> "M001")
        String memberId = memberCombo.getValue().split(" - ")[0].trim();
        String bookId = bookCombo.getValue().split(" - ")[0].trim();

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
        issueDatePicker.setValue(LocalDate.now());
        dueDatePicker.setValue(LocalDate.now().plusDays(14));
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