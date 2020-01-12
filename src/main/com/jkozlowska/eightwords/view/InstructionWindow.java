package main.com.jkozlowska.eightwords.view;

import main.com.jkozlowska.eightwords.model.AllValues;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class InstructionWindow extends Scene {
    private Rectangle rectangle = new Rectangle(AllValues.rectangleWidth,AllValues.rectangleHeight);
    private static InstructionWindow instructionWindow = new InstructionWindow(new BorderPane());
    private Button nextButton = new Button(">");
    private Button backButton = new Button("<");
    private StackPane stackPane = new StackPane();
    private BorderPane root = new BorderPane();
    private VBox vBox = new VBox();
    private static Stage stage;

    public InstructionWindow(BorderPane root) {
        this(root, AllValues.windowWidth, AllValues.windowHeight);
        this.root = root;

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.web(AllValues.COLOR1));
        rectangle.setStrokeWidth(4);
        showDescription();
    }

    private void showDescription() {
        root.getChildren().clear();
        vBox.getChildren().clear();
        stackPane.getChildren().clear();

        Label text = new Label();
        Label text2 = new Label();
        Image img = new Image("main/com/jkozlowska/eightwords/files/boardJPG.jpg");
        ImageView imgView = new ImageView(img);

        nextButton.setOnAction(event -> showButtonsDescription());

        backButton.setOnAction(event -> {
            stage.setScene(MainWindow.getMainWindow());
        });

        backButton.setStyle("-fx-background-color:"+ AllValues.COLOR3+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-size: 25px; -fx-font-weight: bold;");
        nextButton.setStyle("-fx-background-color:"+ AllValues.COLOR3+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-size: 25px; -fx-font-weight: bold;");
        nextButton.setPrefSize(50,600);
        backButton.setPrefSize(50,600);

        text2.setText("IN THE BASIC VERSION");
        text.setText("Eight different letters are arranged in the sixteen fields of the diagram. Fill out the remaining empty boxes with the same letters so that each row, in each column and on each of the diagonals of the square have different letters. After filling the board, enter the password which will be in the yellow fields.");
        text2.setStyle("-fx-control-inner-background:"+AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR1+"; -fx-font-weight: bold; -fx-font-size: 25px;");
        text.setStyle("-fx-control-inner-background:"+AllValues.COLOR3+"; -fx-text-fill:"+AllValues.COLOR3+"; -fx-font-weight: bold; -fx-font-size: 21px;");
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setAlignment(Pos.TOP_CENTER);
        text.setPrefWidth(600);
        text.setWrapText(true);


        vBox.getChildren().addAll(text2, text);
        vBox.setMaxSize(600,530);
        vBox.setAlignment(Pos.CENTER);

        imgView.setFitWidth(300);
        imgView.setFitHeight(300);

        vBox.getChildren().add(imgView);
        stackPane.getChildren().addAll(rectangle, vBox);
        root.setStyle("-fx-background-color: "+ AllValues.COLOR2);
        root.setCenter(stackPane);
        root.setRight(nextButton);
        root.setLeft(backButton);
    }

    private void showButtonsDescription() {
        Image img = new Image("main/com/jkozlowska/eightwords/files/OwnBoardJPG.jpg");
        ImageView imgView = new ImageView(img);
        Label text = new Label("IN YOUR VERSION");
        Label text2 = new Label("You can choose the size of the board!\n" +
                "You can choose characters!\n" +
                "You can set a password fields!\n" +
                "And you can set your own password!\n");

        root.getChildren().clear();
        vBox.getChildren().clear();
        stackPane.getChildren().clear();

        nextButton.setOnAction(event -> {
            stage.setScene(MainWindow.getMainWindow());
            showDescription();
        });

        backButton.setOnAction(event -> showDescription());

        text.setStyle("-fx-control-inner-background:"+AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR1+"; -fx-font-weight: bold; -fx-font-size: 25px;");
        text2.setStyle("-fx-control-inner-background:"+AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR3+"; -fx-font-weight: bold; -fx-font-size: 25px;");
        text2.setTextAlignment(TextAlignment.CENTER);

        vBox.getChildren().addAll(text, text2, imgView);
        stackPane.getChildren().addAll(rectangle, vBox);
        imgView.setFitHeight(300);
        imgView.setFitWidth(300);
        vBox.setSpacing(15);

        root.setCenter(stackPane);
        root.setRight(nextButton);
        root.setLeft(backButton);
    }

    public InstructionWindow(BorderPane root, int width, int height) {
        super(root,width,height);
    }

    public static void setStage(Stage stagge) {
        stage = stagge;
    }

    public static InstructionWindow getInstructionWindow() {
        return instructionWindow;
    }
}

