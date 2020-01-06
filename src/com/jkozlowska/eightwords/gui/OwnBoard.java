package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.Board;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class OwnBoard extends Scene {
    private static BorderPane root = new BorderPane();
    private static Stage stage;
    private int boardSize;
    private Board gameBoard;
    private HBox hBox = new HBox();



    public OwnBoard() {
        this(root,950,650);
        setGameBoardSize();
        gameBoard = new Board(8);

    }


    public OwnBoard(Parent root, int weight, int height) {
        super(root,weight,height);
    }

    public void setGameBoardSize() {
        Text text = new Text("Size of board: (4-10): ");
        hBox.setStyle("-fx-background-color: #49868C;");
        text.setStyle("-fx-font-size:25px;");

        Button fourButton = new Button("4x4");
        Button fiveButton = new Button("5x5");
        Button sixButton = new Button("6x6");
        Button sevenButton = new Button("7x7");
        Button eightButton = new Button("8x8");
        Button nineButton = new Button("9x9");
        Button tenButton = new Button("10x10");

        fourButton.setPrefSize(80,40);
        fiveButton.setPrefSize(80,40);
        sixButton.setPrefSize(80,40);
        sevenButton.setPrefSize(80,40);
        eightButton.setPrefSize(80,40);
        nineButton.setPrefSize(80,40);
        tenButton.setPrefSize(80,40);

        hBox.getChildren().addAll(
                fourButton,
                fiveButton,
                sixButton,
                sevenButton,
                eightButton,
                nineButton,
                tenButton);

        hBox.getChildren().forEach(button -> {
            button.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
        });

        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        root.setRight(text);
        root.setCenter(hBox);
    }



    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
}
