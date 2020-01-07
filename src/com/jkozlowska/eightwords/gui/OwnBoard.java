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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class OwnBoard extends Scene {
    private int numberOfPasswordCells = 0;
    private boolean isYellow = false;
    private BorderPane root = new BorderPane();
    private String value = null;
    private int indexOfLetters = 0;
    private StackPane[][] square;
    private GridPane gridPane = new GridPane();
    private GridPane buttons = new GridPane();
    private char[] letters;
    private static Stage stage;
    private int boardSize;
    private Board gameBoard;
    private VBox vBox;

    public OwnBoard(BorderPane root) {
        this(root,950,650);
        this.root = root;
        setGameBoardSize();
    }

    public void createButtons() {
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: #49868C;");
        buttons.setHgap(50);
        buttons.setAlignment(Pos.TOP_CENTER);

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
        CommandManager commandManager = new CommandManager();
        Label passwordLabel = new Label("Select cells for password");
        HBox hBox = new HBox();
        Button nextButton = new Button("Next");
        Button startButton = new Button("START");


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
                            if(isYellow) {
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
        passwordLabel.setStyle("-fx-font-size:20; -fx-text-fill: #D9B166; -fx-font-weight: bold;");
        startButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");

        startButton.setOnAction(event -> {
            if(numberOfPasswordCells>1) {
                gridPane.getChildren().clear();
                vBox.getChildren().clear();
                hBox.getChildren().clear();
            }
        });

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
            isYellow = true;
            vBox.setSpacing(30);
            vBox.getChildren().addAll(passwordLabel,startButton);
            vBox.setAlignment(Pos.TOP_CENTER);
        });

        nextButton.setStyle("-fx-background-color: #D9B166; -fx-text-fill: #49868C; -fx-font-weight: bold; -fx-font-size: 15px;");
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(50);
        vBox.getChildren().addAll(hBox,nextButton);

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #49868C;");

        root.setLeft(gridPane);
        root.setCenter(vBox);
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
            setEnterForLetterInEditor(textField);

        }
    }

    public void setEnterForLetterInEditor(TextField textField) {
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
