package com.jkozlowska.eightwords.view;

import com.jkozlowska.eightwords.model.AllValues;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InstructionWindow extends Scene {
    private BorderPane root = new BorderPane();
    public static InstructionWindow instructionWindow = new InstructionWindow(new BorderPane());
    private static Stage stage;

    public InstructionWindow(BorderPane root) {
        this(root, 970, 580);
        this.root = root;
        showDescription();
    }

    public void showDescription() {
        Rectangle rectangle = new Rectangle(500,500);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        StackPane stackPane = new StackPane();
        Text text = new Text();
        text.setText("Eight different letters are arranged in the sixteen fields of the diagram.");
        text.setStyle("-fx-control-inner-background:"+AllValues.COLOR3+"; -fx-text-fill:"+AllValues.COLOR1+"; -fx-font-weight: bold; -fx-font-size: 15px;");
        VBox vBox = new VBox();
        stackPane.getChildren().addAll(rectangle, text);
        vBox.setStyle("-fx-background-color: "+ AllValues.COLOR2);
        root.setStyle("-fx-background-color: "+ AllValues.COLOR2);
        root.setCenter(stackPane);
    }

    public InstructionWindow(BorderPane root, int width, int height) {
        super(root,width,height);
    }

    public static void setStage(Stage stagge) {
        stage = stagge;
    }

    public static InstructionWindow getInStructionWindow() {
        return instructionWindow;
    }
}

