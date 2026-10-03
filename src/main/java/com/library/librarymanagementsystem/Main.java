package com.library.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/com/library/librarymanagementsystem/view/login.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        scene.getStylesheets().add(
                Main.class.getResource("/com/library/librarymanagementsystem/css/style.css").toExternalForm()
        );
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}