package com.library.librarymanagementsystem.dto;

public class BookDTO {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private int year;
    private int quantity;

    // Constructor එක (මේක තමයි 'Default constructor is invoked with arguments' error එක හදන්නේ)
    public BookDTO(String bookId, String title, String author, String category, int year, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.quantity = quantity;
    }

    // Getters (මේවා තමයි 'Cannot resolve method' error එක හදන්නේ)
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getYear() { return year; }
    public int getQuantity() { return quantity; }
}