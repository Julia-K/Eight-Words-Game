package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.Board;
import com.jkozlowska.eightwords.Conditions;
import com.jkozlowska.eightwords.commands.AddValueCommand;
import com.jkozlowska.eightwords.commands.CommandManager;
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
import javafx.stage.Stage;

import java.io.*;


public class BoardCreator extends Scene {
    private int numberOfPasswordCells = 0;
    private String password;
    private boolean isPasswordCellStage = false;
    private BorderPane root = new BorderPane();
    private String value = null;
    private int indexOfLetters = 0;
    private StackPane[][] square;
    private GridPane gridPane = new GridPane();
    private char[] letters;
    private static Stage stage;
    private int boardSize;
    private Board gameBoard;
    private VBox vBox;

    public BoardCreator(BorderPane root) {
        this(root,950,580);
        this.root = root;
        setGameBoardSize();
    }

    private void generateInitialBoard() {
        CommandManager commandManager = new CommandManager();
        Label label = new Label(" Select cells\nfor password");
        HBox hBox = new HBox();
        Button nextButton = new Button("NEXT");
        Button nextButton2 = new Button("NEXT");
        Button startButton = new Button("START");
        TextField textField = new TextField();

        nextButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
        label.setStyle("-fx-font-size:20; -fx-text-fill: #D9B166; -fx-font-weight: bold;");
        startButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
        nextButton2.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Rectangle rectangle = new Rectangle(60, 60);
                Text text = new Text();
                square[i][j] = new StackPane();
                int finalI = i;
                int finalJ = j;

                text.setStyle("-fx-control-inner-background:#A0D9D9");
                rectangle.setFill(Color.web("#A0D9D9"));
                rectangle.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");

                square[i][j].getChildren().addAll(rectangle,text);

                square[i][j].getChildren().forEach(item -> { //plansza - dodanie wartosci i pól na hasło
                    item.setOnMouseClicked(mouseEvent -> {
                        Text text1 = new Text(value);
                        char character = value.charAt(0);

                        if(mouseEvent.getClickCount() == 1) {
                            if(isPasswordCellStage) {
                                if(!gameBoard.isFilledCell(finalI,finalJ)) {
                                    if(gameBoard.isPasswordCell(finalI,finalJ)) {
                                        gameBoard.setPasswordCell(finalI, finalJ, false);
                                        rectangle.setFill(Color.web("#A0D9D9"));
                                        square[finalI][finalJ].getChildren().clear();
                                        square[finalI][finalJ].getChildren().add(rectangle);
                                        numberOfPasswordCells--;
                                        System.out.println(numberOfPasswordCells);
                                    } else {
                                        gameBoard.setPasswordCell(finalI,finalJ,true);
                                        rectangle.setFill(Color.web("#D9B166"));
                                        square[finalI][finalJ].getChildren().clear();
                                        square[finalI][finalJ].getChildren().add(rectangle);
                                        numberOfPasswordCells++;
                                        System.out.println(numberOfPasswordCells);
                                    }
                                }
                            } else {
                                commandManager.execute(new AddValueCommand(gameBoard, finalI, finalJ, character));
                                if(Conditions.isValidMove(gameBoard,letters,character,boardSize)) {
                                    if(gameBoard.getValue(finalI,finalJ)!=' ') {
                                        square[finalI][finalJ].getChildren().remove(1);
                                    }
                                    gameBoard.setValue(finalI, finalJ,character);
                                    square[finalI][finalJ].getChildren().add(text1);
                                    wyswietl();
                                } else {
                                    commandManager.undo();
                                }
                            }
                        }
                    });
                });

                gridPane.add(square[i][j],j,i);
            }
        }

        hBox.setStyle("-fx-background-color: #49868C;");
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(10);
        for (int i = 0; i < boardSize; i++) {
            Button button = new Button(letters[i]+"");
            hBox.getChildren().add(button);
            button.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
            button.setPrefSize(100,50);
            button.setOnAction(event -> {
                value = button.getText();
            });
        }
        VBox.setMargin(hBox,new Insets(35,5,0,0));
        hBox.setAlignment(Pos.TOP_CENTER);

        nextButton.setOnAction(event -> {
            for(int i = 0; i < boardSize; i++) {
                for(int j = 0; j < boardSize; j++) {
                    if(gameBoard.getValue(i,j)!=' ') {
                        gameBoard.setCellChangeToPossible(i,j,false);
                    }
                }
            }
            vBox.getChildren().clear();
            hBox.getChildren().clear();
            isPasswordCellStage = true;
            vBox.setSpacing(20);
            label.setPadding(new Insets(50));
            vBox.getChildren().addAll(label,nextButton2);
            vBox.setAlignment(Pos.TOP_CENTER);
        });

        nextButton2.setOnAction(event -> {
            if(numberOfPasswordCells>1) {
                vBox.getChildren().clear();
                hBox.getChildren().clear();
                gridPane.getChildren().clear();

                label.setText("Enter your password: ");
                label.setPadding(new Insets(50));
                textField.setMaxSize(170,50);
                textField.setStyle("-fx-font-size: 20");
                textField.setOnKeyPressed(keyEvent -> {
                    if(keyEvent.getCode() == KeyCode.ENTER) {
                        if(textField.getText().length()==numberOfPasswordCells && Conditions.containsString(textField.getText(),letters)) {
                            password = textField.getText();
                            gameBoard.setPassword(password);
                            vBox.getChildren().clear();
                            hBox.getChildren().clear();
                            try {
                                OwnBoard.setStage(stage);
                                stage.setScene(new OwnBoard(new BorderPane(),gameBoard));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                vBox.setAlignment(Pos.TOP_CENTER);
                vBox.setSpacing(30);
                vBox.getChildren().addAll(label, textField);
            }
        });

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(hBox,nextButton);

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #49868C;");

        root.setLeft(gridPane);
        root.setCenter(vBox);
    }


    private BoardCreator(Parent root, int weight, int height) {
        super(root,weight,height);
    }

    private void setGameBoardSize() {
        HBox hBox = new HBox();
        vBox = new VBox();
        Label text = new Label("Size of board: ");
        vBox.setStyle("-fx-background-color: #49868C;");
        hBox.setStyle("-fx-background-color: #49868C;");
        text.setStyle("-fx-font-size:30px; -fx-text-fill: #D9B166; -fx-font-weight: bold;");

        for(int i = 4; i < 10; i++) {            //przyciski do wyboru rozmiaru planszy
           Button button = new Button(i+"x"+i);
           hBox.getChildren().add(button);
            button.setPrefSize(80, 40);
            button.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
            clickSizeButton(button,i);
        }

        hBox.setSpacing(30);
        vBox.setSpacing(40);
        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(text,hBox);
        root.setCenter(vBox);
    }

    private void clickSizeButton(Button button, int size) { // ustawienie rozmiaru tablicy
        button.setOnAction(event -> {
            boardSize = size;
            square = new StackPane[boardSize][boardSize];
            vBox.getChildren().clear();
            gameBoard = new Board(boardSize);
            letters = new char[boardSize];
            createPlaceForCharacter();
        });
    }

    private void createPlaceForCharacter() {
        if(indexOfLetters<boardSize) {
            Label label = new Label("Enter the "+(indexOfLetters+1)+". allowed character: ");
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
            setEnterForLetterInEditor(textField);

        }
    }

    private void setEnterForLetterInEditor(TextField textField) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String text = textField.getText();
                char character = text.toUpperCase().charAt(0);
                if (!Conditions.contains(letters, character)) {
                    if(indexOfLetters<boardSize) {
                        letters[indexOfLetters] = character;
                        if(indexOfLetters+1<boardSize) {
                            indexOfLetters++;
                            createPlaceForCharacter();
                        } else {
                            vBox.getChildren().clear();
                            gameBoard.setLetters(letters);
                            for (char x : letters) {
                                System.out.println(x);
                            }
                            generateInitialBoard();
                        }
                    }
                }
            }});
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
