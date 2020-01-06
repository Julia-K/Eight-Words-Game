package com.jkozlowska.eightwords;

import com.jkozlowska.eightwords.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Eight Word Game");
        MainWindow.setStage(primaryStage);
        primaryStage.setScene(MainWindow.getMainWindow());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
