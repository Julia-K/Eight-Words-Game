package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.Board;
import com.jkozlowska.eightwords.Conditions;
import com.jkozlowska.eightwords.ReadBoard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.Optional;

public class ExampleBoard extends Scene {
    private static BorderPane root = new BorderPane();
    private StackPane[][] square = new StackPane[8][8];
    public static ExampleBoard exampleBoard;
    private ReadBoard readBoard = new ReadBoard("exampleBoard.txt");
    private Board gameBoard = readBoard.getGameBoard();
    private GridPane gridPane = new GridPane();
    private GridPane buttons = new GridPane();

    static {
        try {
            exampleBoard = new ExampleBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ExampleBoard() throws IOException {
        this(root,950,650);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #49868C;");

        gameBoard.setPasswordCell(1,3,true);
        gameBoard.setPasswordCell(3,5,true);
        gameBoard.setPasswordCell(4, 2, true);
        gameBoard.setPasswordCell(6, 4, true);
        update();

        buttons.setHgap(15);

        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button save = new Button("Save");
        Button load = new Button("Load");
        buttons.add(undo, 0, 10);
        buttons.add(redo,1,10);
        buttons.add(save,0,11);
        buttons.add(load,1,11);

        undo.setPrefSize(170, 50);
        redo.setPrefSize(170, 50);
        save.setPrefSize(170,50);
        load.setPrefSize(170,50);

        buttons.setAlignment(Pos.TOP_CENTER);

        undo.setOnAction(event -> {
            gameBoard.undo();
            //commandManager.undo();
            update();
        });

        redo.setOnAction(event -> {
            gameBoard.redo();
            //commandManager.redo();
            update();
        });

        save.setOnAction(event -> {
            try {
                save(gameBoard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        load.setOnAction(event -> {
            try {
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        root.setLeft(gridPane);
        root.setCenter(buttons);
    }

    private void save(Board board) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        File file = fileChooser.showSaveDialog(owner);

        if(file!=null) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(board);
            out.close();
        }
    }

    private void load() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        File file = fileChooser.showOpenDialog(owner);

        if(file!=null) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            try {
                this.gameBoard = (Board) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            in.close();
            update();
        }
    }



    private ExampleBoard(Parent root, int width, int height) throws IOException {
        super(root,width,height);
    }

    private void setEnter(TextField textArea, int row, int col) {
        textArea.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String text = textArea.getText();
                char character = text.toUpperCase().charAt(0);
                if(character!=gameBoard.getValue(row,col)) {
                    gameBoard.addValueWithHistory(row,col,character);
                    //commandManager.execute(new AddValueCommand(gameBoard, row, col, character));
                    if (!Conditions.isValidMove(gameBoard, character)) {
                        System.out.println(character + " nie moze byc w tym miejscu");
                        gameBoard.undo();
                        //commandManager.undo();
                        textArea.setText("");
                    } else {
                        wyswietl();
                        textArea.setText(Character.toString(character));
                    }
                }
            }
        });
    }

    public static ExampleBoard getBoard() {
        return exampleBoard;
    }

    public void update() {
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                TextField textArea = createTextField();
                Text text = new Text();
                setEnter(textArea, i, j);
                square[i][j] = new StackPane();
                Rectangle rectangle = new Rectangle(60, 60);
                if(gameBoard.isPasswordCell(i,j)) {
                    textArea.setStyle("-fx-control-inner-background:#D9B166");
                    rectangle.setFill(Color.web("#D9B166"));
                } else {
                    rectangle.setFill(Color.web("#A0D9D9"));
                    rectangle.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
                }
                if (!gameBoard.isCellChangePossible(i, j)) {
                    text.setText(Character.toString(gameBoard.getValue(i,j)));
                    square[i][j].getChildren().addAll(rectangle,text);
                } else if (gameBoard.isFilledCell(i,j)){
                    text.setText(Character.toString(gameBoard.getValue(i,j)));
                    square[i][j].getChildren().addAll(rectangle,text);
                } else {
                    square[i][j].getChildren().addAll(rectangle,textArea);
                }
                gridPane.add(square[i][j],j,i);
            }
        }
        addGridEvent(square);

        if (gameBoard.areFilledAll()) {
            TextInputDialog dialog = new TextInputDialog("solution");
            dialog.setTitle("Congrats!");
            dialog.setHeaderText("You filled whole board!");
            dialog.setContentText("Please enter the word-solution:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(word -> {
                if(word.equals(readBoard.getPassword())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congrats!");
                    alert.setHeaderText(null);
                    alert.setContentText("Congratulations! You solve the puzzle!");
                    alert.showAndWait();
                } else {
                    ////////????????????????
                }
            });
        }
    }

    private void addGridEvent(StackPane[][] square) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                TextField textArea = createTextField();
                setEnter(textArea, i, j);
                if(gameBoard.isCellChangePossible(i,j)) {
                    int finalI = i;
                    int finalJ = j;
                    square[i][j].getChildren().forEach(item -> {
                        item.setOnMouseClicked(mouseEvent -> {
                            if(mouseEvent.getClickCount() == 2) {
                                System.out.println("hihiihih");
                                square[finalI][finalJ].getChildren().addAll(textArea);
                            }
                        });
                    });
                }
            }
        }
    }

    public TextField createTextField() {
        TextField textArea = new TextField();
        textArea.setMaxSize(60, 60);
        textArea.setStyle("-fx-control-inner-background:#A0D9D9");
        //ograniczenie wpisywania tekstu do jednej litery
        textArea.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null;
            } else {
                return change;
            }
        }));

        return textArea;
    }

    public void wyswietl() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("|"+gameBoard.getValue(i,j));
            }
            System.out.println("|");
        }
        System.out.println("---------------------------");;
    }
}
