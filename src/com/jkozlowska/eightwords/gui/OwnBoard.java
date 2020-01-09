package com.jkozlowska.eightwords.gui;

import com.jkozlowska.eightwords.model.AllValues;
import com.jkozlowska.eightwords.model.Board;
import com.jkozlowska.eightwords.model.Conditions;
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

import java.io.*;
import java.util.Optional;

public class OwnBoard extends Scene {
    private BorderPane root = new BorderPane();
    private static Stage stage;
    private StackPane[][] square;
    private Board gameBoard;
    private GridPane gridPane = new GridPane();
    private GridPane buttons = new GridPane();


    public OwnBoard(BorderPane root, Board board) throws IOException {
        this(root,970,580);
        this.root = root;
        gameBoard = board;
        square = new StackPane[board.getSize()][board.getSize()];
        createButtonsAndSetStyle();
    }

    public OwnBoard(Parent root, int width, int height) throws IOException {
        super(root,width,height);
    }

    private void createButtonsAndSetStyle() {
        gridPane.setPadding(new Insets(10,10,10,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color:"+AllValues.COLOR2);
        buttons.setPadding(new Insets(0,10,0,0));
        buttons.setStyle("-fx-background-color:"+AllValues.COLOR2);
        buttons.setVgap(20);
        buttons.setHgap(20);
        buttons.setAlignment(Pos.CENTER_LEFT);

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
            (buttons.getChildren().get(i)).setStyle("-fx-background-color:"+AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");
            ((Button)buttons.getChildren().get(i)).setPrefSize(170,50);
        }

        undoButton.setOnAction(event -> {
            gameBoard.undo();
            update();
        });

        redoButton.setOnAction(event -> {
            gameBoard.redo();
            update();
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

        update();
        buttons.setAlignment(Pos.TOP_CENTER);

        root.setLeft(gridPane);
        root.setCenter(buttons);
    }

    private void update() {
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                TextField textArea = createTextField();
                Text text = new Text();
                text.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
                Rectangle rectangle = new Rectangle((560-(10*gameBoard.getSize()+10))/gameBoard.getSize(), (560-(10*gameBoard.getSize()+10))/gameBoard.getSize());
                rectangle.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");

                setEnter(textArea, i, j); //ustawienie zapisywania wartosci po kliknieciu Entera
                square[i][j] = new StackPane();
                if(gameBoard.isPasswordCell(i,j)) {
                    textArea.setStyle("-fx-control-inner-background:"+AllValues.COLOR1);
                    rectangle.setFill(Color.web(AllValues.COLOR1));
                } else {
                    rectangle.setFill(Color.web(AllValues.COLOR3));
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
        endGame();
    }

    private void endGame() {
        if (gameBoard.areFilledAll()) {
            TextInputDialog dialog = new TextInputDialog("solution");
            dialog.setTitle("Congrats!");
            dialog.setHeaderText("You filled whole board!");
            dialog.setContentText("Please enter the word-solution:");
            Optional<String> result = dialog.showAndWait();

            boolean check = true;
            while(check) {
                String word = result.get();
                if(word.toUpperCase().equals(gameBoard.getPassword())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congrats!");
                    alert.setHeaderText(null);
                    alert.setContentText("Congratulations! You solve the puzzle!");
                    alert.showAndWait();
                    check = false;
                } else {
                    result = dialog.showAndWait();
                }
            }

        }
    }

    private void addGridEvent(StackPane[][] square) {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                TextField textArea = createTextField();
                setEnter(textArea, i, j);
                if(gameBoard.isCellChangePossible(i,j)) {
                    int finalI = i;
                    int finalJ = j;
                    //klikniecie dwa razy - mozliwosc edycji
                    square[i][j].getChildren().forEach(item -> {
                        item.setOnMouseClicked(mouseEvent -> {
                            if(mouseEvent.getClickCount() == 2) {
                                if(gameBoard.isPasswordCell(finalI,finalJ)) {
                                    textArea.setStyle("-fx-control-inner-background:"+AllValues.COLOR1);
                                }
                                square[finalI][finalJ].getChildren().addAll(textArea);
                            }
                        });
                    });
                }
            }
        }
    }

    private TextField createTextField() {
        TextField textArea = new TextField();
        textArea.setPrefSize((560-(10*gameBoard.getSize()+10))/gameBoard.getSize(),(560-(10*gameBoard.getSize()+10))/gameBoard.getSize());
        textArea.setStyle("-fx-control-inner-background:"+AllValues.COLOR3+"; -fx-arc-height: 10; -fx-arc-width: 10;");
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

    private void setEnter(TextField textArea, int row, int col) {
        textArea.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String text = textArea.getText();
                char character = text.toUpperCase().charAt(0);
                if(character!=gameBoard.getValue(row,col)) {
                    gameBoard.addValueWithHistory(row,col,character);
                    if (!Conditions.isValidMove(gameBoard, gameBoard.getLetters(), character,gameBoard.getSize())) {
                        System.out.println(character + " nie moze byc w tym miejscu");
                        gameBoard.undo();
                        textArea.setText("");
                    } else {
                        wyswietl();
                        textArea.setText(Character.toString(character));
                    }
                }
                update();
            }
        });
    }

    private void wyswietl() {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if(gameBoard.isFilledCell(i,j)) {
                    System.out.print("|"+gameBoard.getValue(i,j));
                } else {
                    System.out.print("|"+gameBoard.getValue(i,j));
                }
            }
            System.out.println("|");
        }
        System.out.println("---------------------------");;
    }

    private void save(Board board) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(getWindow());

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
        File file = fileChooser.showOpenDialog(getWindow());

        if(file!=null) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            try {
                this.gameBoard = (Board) in.readObject();
                System.out.println("rozmiar: " + gameBoard.getSize());
                square = new StackPane[gameBoard.getSize()][gameBoard.getSize()];
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            in.close();
            gridPane.getChildren().clear();
            update();
        }
    }

     static void setStage(Stage stagge) {
        stage = stagge;
    }
}
