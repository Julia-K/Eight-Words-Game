package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.Board;
import com.jkozlowska.eightwords.Conditions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class OwnBoard extends Scene {
    public BorderPane root = new BorderPane();
    private int indexOfLetters = 0;
    private StackPane[][] square;
    private GridPane gridPane = new GridPane();
    private GridPane buttons = new GridPane();
    private char[] letters;
    private static Stage stage;
    private int boardSize;
    private Board gameBoard;
    private VBox vBox;

    public OwnBoard(BorderPane xd) {
        this(xd,950,650);
        this.root = xd;
        setGameBoardSize();
    }

    public void createButtons() {
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: #49868C;");

        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        Button exit_to_menuButton = new Button("Exit to menu");
        Button exitButton = new Button("Exit");

        buttons.add(undoButton, 0, 10);
        buttons.add(redoButton,1,10);
        buttons.add(saveButton,0,11);
        buttons.add(loadButton,1,11);
        buttons.add(exit_to_menuButton,0,12);
        buttons.add(exitButton,1,12);

        for(int i = 0; i < 6; i++) {
            ((Button)buttons.getChildren().get(i)).setPrefSize(170,50);
        }

        buttons.setHgap(50);
        buttons.setAlignment(Pos.TOP_CENTER);

        undoButton.setOnAction(event -> {
            gameBoard.undo();
            //update();
        });

        redoButton.setOnAction(event -> {
            gameBoard.redo();
            //update();
        });

        saveButton.setOnAction(event -> {
            try {
                save(gameBoard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loadButton.setOnAction(event -> {
            try {
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        exit_to_menuButton.setOnAction(event -> {
            stage.setScene(MainWindow.getMainWindow());

        });

        exitButton.setOnAction(event -> {
            getWindow().hide();
        });
    }

    public void generateInitialBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                TextField textArea = DefaultBoard.createTextField();
                Text text = new Text();
                Rectangle rectangle = new Rectangle(60, 60);
                //setEnter(textArea, i, j); //ustawienie zapisywania wartosci po kliknieciu Entera
                square[i][j] = new StackPane();

                rectangle.setFill(Color.web("#A0D9D9"));
                rectangle.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");

                square[i][j].getChildren().addAll(rectangle,textArea);

                gridPane.add(square[i][j],j,i);
            }
        }
        //addGridEvent(square);

        HBox hBox = new HBox(); 
        hBox.setStyle("-fx-background-color: #49868C;");
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(10);
        for (int i = 0; i < boardSize; i++) {
            //if(i)
            Button button = new Button(letters[i]+"");
            hBox.getChildren().add(button);
            button.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
            button.setPrefSize(100,50);
        }

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #49868C;");

        root.setLeft(gridPane);
        root.setCenter(hBox);
    }


    public OwnBoard(Parent root, int weight, int height) {
        super(root,weight,height);
    }

    private void setGameBoardSize() {
        HBox hBox = new HBox();
        vBox = new VBox();
        Label text = new Label("Size of board: ");
        vBox.setStyle("-fx-background-color: #49868C;");
        hBox.setStyle("-fx-background-color: #49868C;");
        text.setStyle("-fx-font-size:30px; -fx-text-fill: #D9B166; -fx-font-weight: bold;");

        Button fourButton = new Button("4x4");
        Button fiveButton = new Button("5x5");
        Button sixButton = new Button("6x6");
        Button sevenButton = new Button("7x7");
        Button eightButton = new Button("8x8");
        Button nineButton = new Button("9x9");

        hBox.getChildren().addAll(
                fourButton,
                fiveButton,
                sixButton,
                sevenButton,
                eightButton,
                nineButton
        );

        for(int i = 4; i <= 9; i++) {
            ((Button)hBox.getChildren().get(i-4)).setPrefSize(80,40);
            hBox.getChildren().get(i-4).setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
            clickSizeButton((Button)hBox.getChildren().get(i-4),i);
        }

        hBox.setSpacing(30);
        vBox.setSpacing(40);
        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(text,hBox);
        root.setCenter(vBox);
    }

    private void clickSizeButton(Button button, int size) {
        button.setOnAction(event -> {
            boardSize = size;
            square = new StackPane[boardSize][boardSize];
            vBox.getChildren().clear();
            gameBoard = new Board(boardSize);
            //gameBoard = new Board(boardSize);
            letters = new char[boardSize];
            createPlaceForLetter();
            //wyswietl();
        });
    }

    private void createPlaceForLetter() {
        if(indexOfLetters<boardSize) {
            Label label = new Label("Enter your "+(indexOfLetters+1)+". letter: ");
            vBox.getChildren().clear();
            HBox hBox = new HBox();
            hBox.setStyle("-fx-background-color: #49868C;");
            label.setStyle("-fx-font-size:30px; -fx-text-fill: #D9B166; -fx-font-weight: bold;");
            vBox.getChildren().add(label);

            TextField textField = new TextField();
            textField.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
                String newText = change.getControlNewText();
                if (newText.length() > 1) {
                    return null;
                } else {
                    return change;
                }
            }));

            textField.setPrefSize(80,40);
            textField.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");

            hBox.getChildren().add(textField);

            vBox.getChildren().addAll(hBox);
            hBox.setSpacing(30);
            vBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);
            vBox.setAlignment(Pos.CENTER);
            root.setCenter(vBox);
            setEnterForLetter(textField);

        }
    }

    public void setEnterForBoardCell(TextField textField) {
        textField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                String text = textField.getText();
                char character = text.toUpperCase().charAt(0);

            }
        });
    }

    public void setEnterForLetter(TextField textField) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String text = textField.getText();
                char character = text.toUpperCase().charAt(0);
                if (!Conditions.contains(letters, character)) {
                    if(indexOfLetters<boardSize) {
                        letters[indexOfLetters] = character;
                        if(indexOfLetters+1<boardSize) {
                            indexOfLetters++;
                            createPlaceForLetter();
                        } else {
                            vBox.getChildren().clear();
                            for (char x : letters) {
                                System.out.println(x);
                            }
                            generateInitialBoard();
                        }
                    }
                }
            }});
    }

    private void save(Board board) throws IOException {
        /*FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(getWindow());

        if(file!=null) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(board);
            out.close();
        } */
    }

    private void load() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(getWindow());

        if(file!=null) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            try {
                this.gameBoard = (Board) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            in.close();
           // update();
        }
    }

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void wyswietl() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print("|"+gameBoard.getValue(i,j));
            }
            System.out.println("|");
        }
        System.out.println("---------------------------");;
    }
}
