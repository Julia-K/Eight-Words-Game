package com.jkozlowska.eightwords.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.Serializable;

public class MainWindow extends Scene implements Serializable {
    private static BorderPane borderPane = new BorderPane();
    //private static MainWindow mainWindow = new MainWindow();
    private Button startButton = new Button("Start with ");
    private Button loadGameButton = new Button("Load game");
    private Button yourOwnBoardButton = new Button("Set your own board");
    private Button instructionButton = new Button("Instruction");
    private Button exitButton = new Button("Exit");
    private Label sceneTitle = new Label("Eight Word Game");

    public MainWindow(Stage primaryStage) {
        this(borderPane,950 ,650);
        VBox vBox = new VBox(5);

        sceneTitle.setStyle("-fx-font-size:48; -fx-text-fill: #D9B166; -fx-font-weight: bold;");
        instructionButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold;");
        startButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold;");
        loadGameButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold;");
        yourOwnBoardButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold;");
        exitButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold;");
        vBox.setStyle("-fx-background-color: #49868C;");
        borderPane.setCenter(vBox);
        borderPane.setStyle("-fx-background-color: #D9B166;");
        borderPane.setPadding(new Insets(15,15,15,15));

        vBox.getChildren().add(sceneTitle);
        vBox.getChildren().add(startButton);
        vBox.getChildren().add(loadGameButton);
        vBox.getChildren().add(yourOwnBoardButton);
        vBox.getChildren().add(instructionButton);
        vBox.getChildren().add(exitButton);

        vBox.setAlignment(Pos.CENTER);

        startButton.setOnAction(e -> primaryStage.setScene(ExampleBoard.getBoard()));




        startButton.setPrefSize(170, 50);
        yourOwnBoardButton.setPrefSize(170, 50);
        instructionButton.setPrefSize(170,50);
        exitButton.setPrefSize(170, 50);
        loadGameButton.setPrefSize(170,50);

        VBox.setMargin(startButton, new Insets(70.0,10,10,10));
        VBox.setMargin(yourOwnBoardButton, new Insets(10.0));
        VBox.setMargin(exitButton, new Insets(10.0));
    }

    private MainWindow(Parent root, double width, double height) {
        super(root,width,height);
    }

    /*public static MainWindow getMainWindow() {
        return mainWindow;
    } */

    public Button getStartButton() {
        return startButton;
    }
}
