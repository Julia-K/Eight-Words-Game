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

import java.io.*;
import java.util.Optional;

public class DefaultBoard extends Scene {
    private static BorderPane root = new BorderPane();
    private StackPane[][] square = new StackPane[8][8];
    private final static char[] eightLetters = {'O', 'S', 'I', 'E', 'M', 'L', 'T', 'R'};
    public static DefaultBoard exampleBoard;
    private ReadBoard readBoard;
    private Board gameBoard;
    private GridPane gridPane = new GridPane();
    private GridPane buttons = new GridPane();
   // Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
    private static Stage stage;

    static {
        try {
            exampleBoard = new DefaultBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DefaultBoard() throws IOException {
        this(root,950,650);
        readBoard = new ReadBoard("exampleBoard.txt");
        gameBoard = readBoard.getGameBoard();
        buttons.setPadding(new Insets(10));
        buttons.setStyle("-fx-background-color: #49868C;");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #49868C;");

        setFourPasswordCells();

       // buttons.setHgap(15);

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

        //buttons.setHgap(50);

        buttons.setAlignment(Pos.TOP_CENTER);

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
            try {
                readBoard = new ReadBoard("exampleBoard.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameBoard = readBoard.getGameBoard();
            setFourPasswordCells();
            stage.setScene(MainWindow.getMainWindow());

        });

        exitButton.setOnAction(event -> {
            getDefaultBoard().getWindow().hide();
        });

        root.setLeft(gridPane);
        root.setCenter(buttons);
    }

    private void save(Board board) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

       // Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
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
       // Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        File file = fileChooser.showOpenDialog(getWindow());

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

    private DefaultBoard(Parent root, int width, int height) throws IOException {
        super(root,width,height);
    }

    private void setEnter(TextField textArea, int row, int col) {
        textArea.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String text = textArea.getText();
                char character = text.toUpperCase().charAt(0);
                if(character!=gameBoard.getValue(row,col)) {
                    gameBoard.addValueWithHistory(row,col,character);
                    if (!Conditions.isValidMove(gameBoard, eightLetters, character,8)) {
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

    public static DefaultBoard getDefaultBoard() {
        return exampleBoard;
    }

    private void update() {
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                TextField textArea = createTextField();
                Text text = new Text();
                Rectangle rectangle = new Rectangle(60, 60);
                setEnter(textArea, i, j); //ustawienie zapisywania wartosci po kliknieciu Entera
                square[i][j] = new StackPane();

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
        endGame();
    }

    private void endGame() {
        if (gameBoard.areFilledAll()) {
            TextInputDialog dialog = new TextInputDialog("solution");
            dialog.setTitle("Congrats!");
            dialog.setHeaderText("You filled whole board!");
            dialog.setContentText("Please enter the word-solution:");
            Optional <String> result = dialog.showAndWait();

            boolean check = true;
            while(check) {
                String word = result.get();
                if(word.toUpperCase().equals(readBoard.getPassword())) {
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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
                                    textArea.setStyle("-fx-control-inner-background:#D9B166");
                                }
                                square[finalI][finalJ].getChildren().addAll(textArea);
                            }
                        });
                    });
                }
            }
        }
    }

    public static TextField createTextField() {
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

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public void setFourPasswordCells() {
        gameBoard.setPasswordCell(1,3,true);
        gameBoard.setPasswordCell(3,5,true);
        gameBoard.setPasswordCell(4, 2, true);
        gameBoard.setPasswordCell(6, 4, true);
        update();
    }
}
