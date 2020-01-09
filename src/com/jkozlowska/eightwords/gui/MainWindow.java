package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.AllValues;
import com.jkozlowska.eightwords.Board;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;

public class MainWindow extends Scene implements Serializable {
    private static BorderPane borderPane = new BorderPane();
    private static Stage stage;
    private static MainWindow mainWindow= new MainWindow();

    private MainWindow() {
        this(borderPane,970 ,580);

        Button startButton = new Button("Start");
        Button yourOwnBoardButton = new Button("Set your own board");
        Button instructionButton = new Button("Instruction");
        Button exitButton = new Button("Exit");
        Label sceneTitle = new Label("Eight Word Game");
        VBox vBox = new VBox(5);

        sceneTitle.setStyle("-fx-font-size:48; -fx-text-fill:"+AllValues.COLOR1+"; -fx-font-weight: bold;");
        vBox.setStyle("-fx-background-color:"+AllValues.COLOR2);

        borderPane.setCenter(vBox);
        borderPane.setStyle("-fx-background-color: "+AllValues.COLOR1);
        borderPane.setPadding(new Insets(15,15,15,15));
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(sceneTitle);
        vBox.getChildren().add(startButton);
        vBox.getChildren().add(yourOwnBoardButton);
        vBox.getChildren().add(instructionButton);
        vBox.getChildren().add(exitButton);

        for (int i=1; i < 5; i++) {
            vBox.getChildren().get(i).setStyle("-fx-background-color:"+ AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold;");
            ((Button)vBox.getChildren().get(i)).setPrefSize(170,50);
        }

        VBox.setMargin(startButton, new Insets(70.0,10,10,10));
        VBox.setMargin(yourOwnBoardButton, new Insets(10.0));
        VBox.setMargin(instructionButton,new Insets(10));
        VBox.setMargin(exitButton, new Insets(10.0));

        startButton.setOnAction(event -> {
            try {
                loadDefaultBoard();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        yourOwnBoardButton.setOnAction(event -> {
            BoardCreator.setStage(stage);
            BorderPane tempBorder = new BorderPane();
            BoardCreator newBoardCreator = new BoardCreator(tempBorder);
            stage.setScene(newBoardCreator);
        });

        exitButton.setOnAction(event -> {
            getMainWindow().getWindow().hide();
        });
    }

    private void loadDefaultBoard() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("DefaultBoard.txt"));
        Board board = (Board) in.readObject();
        OwnBoard.setStage(stage);
        stage.setScene(new OwnBoard(new BorderPane(),board));
    }

    private MainWindow(Parent root, double width, double height) {
        super(root,width,height);
    }

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }
}
