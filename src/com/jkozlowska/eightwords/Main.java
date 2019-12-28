package com.jkozlowska.eightwords;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main {

    //@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        //launch(args);
        ReadBoard readBoard = new ReadBoard("exampleBoard.txt");
        TemporaryHelper help = new TemporaryHelper();
        help.wyswietl(readBoard.getGameBoard());
        System.out.println("-------------------");
        help.podaj(readBoard.getGameBoard());
    }
}
