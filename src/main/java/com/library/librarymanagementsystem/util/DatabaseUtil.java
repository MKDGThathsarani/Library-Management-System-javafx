package com.library.librarymanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {

    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    //Data file path
    private static final String DB_URL = "jdbc:sqlite:library.db";

    private DatabaseUtil() {
        // Prevent instantiation
    }
    //getConnection() method
    public static Connection getConnection()  throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

    //Table hadana method eka(app start + load)
    public static void initializeDatabase(){
        //user table
        String userTable = "CREATE TABLE IF NOT EXISTS users ("
                + "username TEXT PRIMARY KEY, "
                + "password TEXT NOT NULL)";

        //book table
        String bookTable = "CREATE TABLE IF NOT EXISTS books ("
                + "book_id TEXT PRIMARY KEY, "
                + "title TEXT NOT NULL, "
                + "author TEXT,"
                + "category TEXT, "
                + "year INTEGER, "
                + "quantity INTEGER)";

        // members table - members සඳහා
        String membersTable = "CREATE TABLE IF NOT EXISTS members ("
                + "member_id TEXT PRIMARY KEY, "
                + "name TEXT NOT NULL, "
                + "email TEXT, "
                + "phone TEXT, "
                + "address TEXT)";

        // borrow_records table - issue/return history සඳහා
        String borrowTable = "CREATE TABLE IF NOT EXISTS borrow_records ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "member_id TEXT, "
                + "book_id TEXT, "
                + "issue_date TEXT, "
                + "due_date TEXT, "
                + "return_date TEXT, "
                + "status TEXT)";
try(Connection conn = getConnection();
        Statement stmt = conn.createStatement()){

    //Table 4
    stmt.execute(userTable);
    stmt.execute(bookTable);
    stmt.execute(membersTable);
    stmt.execute(borrowTable);

    //Default admin user
    String insertAdmin = "INSERT OR IGNOR INTO users(username, password) "
            + "VALUES ('admin' , 'admin123')";
    stmt.execute(insertAdmin);

    LOGGER.info("Database initialized successfully.");
            }catch (SQLException e){
    LOGGER.log(Level.SEVERE, "Database initialization failed.", e);
            e.printStackTrace();
        }
    }
}


