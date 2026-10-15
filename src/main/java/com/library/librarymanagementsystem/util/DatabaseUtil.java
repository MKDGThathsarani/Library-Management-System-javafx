package com.library.librarymanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    //Data file path
    private static final String DB_URL = "jdbc:sqlite:library.db";

    //getConnection() method
    public static Connection getConnection()  throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }
}
