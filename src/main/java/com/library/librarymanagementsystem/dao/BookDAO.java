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

    //all book method
    public java.util.List<Book> getAllBooks() {
        java.util.List<Book> books = new java.util.ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseUtil.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book b = new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getInt("quantity")
                );
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;

    }

    //Search method
    public java.util.List<Book> searchBooks (String keyword){
        java.util.List<Book> books = new java.util.ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR book_id LIKE ?";

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getInt("quantity")

                );
                books.add(b);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
        }
    }

