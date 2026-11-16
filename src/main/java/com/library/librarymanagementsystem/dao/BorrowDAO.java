package com.library.librarymanagementsystem.dao;

import com.library.librarymanagementsystem.dto.BorrowRecordDTO;
import com.library.librarymanagementsystem.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    // 1. Book Issue කරන method එක
    public boolean issueBook(String memberId, String bookId, String issueDate, String dueDate) {
        String sql = "INSERT INTO borrow_records (member_id, book_id, issue_date, due_date, status) " +
                "VALUES (?, ?, ?, ?, 'Borrowed')";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setString(2, bookId);
            ps.setString(3, issueDate);
            ps.setString(4, dueDate);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Book Return කරන method එක
    public boolean returnBook(int recordId, String returnDate, String status) {
        String sql = "UPDATE borrow_records SET return_date = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, returnDate);
            ps.setString(2, status);
            ps.setInt(3, recordId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. History Table එකට හැම record එකක්ම ගන්න method එක (දැන් List<BorrowRecordDTO> එකක් return කරනවා)
    public List<BorrowRecordDTO> getAllRecords() {
        List<BorrowRecordDTO> records = new ArrayList<>();
        String sql = "SELECT * FROM borrow_records ORDER BY id DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                records.add(new BorrowRecordDTO(
                        rs.getString("member_id"),
                        rs.getString("book_id"),
                        rs.getString("issue_date"),
                        rs.getString("due_date"),
                        rs.getString("return_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}