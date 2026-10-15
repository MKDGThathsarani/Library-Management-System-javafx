package com.library.librarymanagementsystem.dao;

import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO  {

    //book ekak database ekata add karana method eka
    public boolean addBook(Book book){
        String sql = "INSERT INTO books (book_id, title, author, category, year, quantity)"
                + "VALUES (?,?,?,?,?,?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, book.getBookId());
            ps.setString(2,book.getTitle());
            ps.setString(3,book.getAuthor());
            ps.setString(4,book.getCategory());
            ps.setInt(5,book.getYear());
            ps.setInt(6,book.getQuantity());

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
