package com.jkozlowska.eightwords.gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TempBoard extends Scene {
    private static GridPane[][] screen_buttons = new GridPane[8][8];
    private static BorderPane root = new BorderPane();
    private static TempBoard tempBoard = new TempBoard();
    Rectangle rec = new Rectangle(50,50);

    private TempBoard() {
        this(root,650,650);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        for (int y=0;y<screen_buttons.length;y++) {
            for (int x=0;x<screen_buttons[y].length;x++) {
                screen_buttons[y][x] = new GridPane();
                Rectangle rec = new Rectangle(50,50);
                rec.setFill(Color.YELLOWGREEN);
                rec.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
                Label label = new Label(" ");
                screen_buttons[y][x].getChildren().addAll(rec);
                grid.add(screen_buttons[y][x], x, y);
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
            root.setBottom(controls);
    }
    private TempBoard(Parent root, int width, int height) {
        super(root,width,height);
    }

    public static TempBoard getTempBoard() {
        return tempBoard;
    }
}
