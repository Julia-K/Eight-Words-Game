package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.Board;

import com.jkozlowska.eightwords.ReadBoard;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class DefaultBoard extends Scene {
    ReadBoard readBoard;

    {
        try {
            readBoard = new ReadBoard("exampleBoard.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Board gameBoard = readBoard.getGameBoard();


    private static StackPane[][] screen_buttons = new StackPane[8][8];
    private static BorderPane root = new BorderPane();
    public static DefaultBoard board = new DefaultBoard();
    Rectangle rec = new Rectangle(50,50);

    private DefaultBoard() {
        this(root,950,650);
        gameBoard.setPasswordNeededCell(2, 4, true);
        gameBoard.setPasswordNeededCell(4, 6, true);
        gameBoard.setPasswordNeededCell(5, 3, true);
        gameBoard.setPasswordNeededCell(7, 5, true);
        VBox vBox = new VBox(5);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: #49868C;");

        for (int x=0;x<screen_buttons.length;x++) {
            for (int y=0;y<screen_buttons[x].length;y++) {
                TextArea textArea = new TextArea();
                textArea.setMaxSize(60,60);
                textArea.setStyle("-fx-control-inner-background:#A0D9D9");
                //textArea.setStyle("-fx-padding: 0 10 0 10");
                screen_buttons[x][y] = new StackPane();
                Rectangle rec = new Rectangle(60,60);
                if(gameBoard.isPasswordNeededCell(x,y)) {
                    textArea.setStyle("-fx-control-inner-background:#A62D2D");
                } else {
                    rec.setFill(Color.web("#A0D9D9"));
                }
                rec.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
                if(!gameBoard.getChangePossibilityCell(x,y)) {
                    screen_buttons[x][y].getChildren().addAll(rec,new Text(Character.toString(gameBoard.getValue(x,y))));
                } else {
                    screen_buttons[x][y].getChildren().add(textArea);
                }
                Label label = new Label(" ");
                //screen_buttons[x][y].getChildren().addAll(rec,new Text(Character.toString(gameBoard.getValue(x,y))));
                grid.add(screen_buttons[x][y], x,y);
            }
        }
        //screen_buttons[0][1].getChildren().addAll(rec, new Label("O"));

        //container for controls
        GridPane controls = new GridPane();

        Button[] function_buttons = new Button[4];
        String[] function_id = {"Hint", "Clear", "Pause", "Check"};
        int pos = 0;
        for (Button b : function_buttons) {
            if (function_id[pos] == "Hint") {
                b = new Button(function_id[pos]);
                controls.add(b, 1, pos+10);
            } else if (function_id[pos] == "Clear"){
                b = new Button(function_id[pos]);
                controls.add(b, 1, pos+10);
            } else if (function_id[pos] == "Pause"){
                b = new Button(function_id[pos]);
                controls.add(b, 1, pos+10);
            } else {
                b = new Button(function_id[pos]);
                controls.add(b, 6, 11);
            }
            b.setStyle("-fx-pref-width: 100px; -fx-pref-height: 50px;");
            pos++;
        }

        Button[] click_buttons = new Button[9];
        pos = 1;
        for (int y=10;y<=12;y++) {
            for (int x=2;x<=4;x++) {
                click_buttons[pos-1] = new Button(Integer.toString(pos));
                controls.add(click_buttons[pos-1], x, y);
                click_buttons[pos-1].setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px;");
                pos++;
            }
        }

        root.setCenter(grid);
        root.setRight(controls);
    }
    private DefaultBoard(Parent root, int width, int height) {
        super(root,width,height);
    }

    public static DefaultBoard getBoard() {
        return board;
    }
}
