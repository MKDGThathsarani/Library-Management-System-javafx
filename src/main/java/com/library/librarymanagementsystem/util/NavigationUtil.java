package com.library.librarymanagementsystem.util;

import com.library.librarymanagementsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationUtil {

    public static void navigateTo(String fxmlFile, String title, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationUtil.class.getResource("/com/library/librarymanagementsystem/view/" + fxmlFile)
            );
            Scene scene = new Scene(loader.load(), width, height);
            scene.getStylesheets().add(
                    NavigationUtil.class.getResource("/com/library/librarymanagementsystem/css/style.css").toExternalForm()
            );
            Stage stage = Main.getPrimaryStage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}