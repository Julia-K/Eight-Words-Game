package main.com.jkozlowska.eightwords.view;

import main.com.jkozlowska.eightwords.model.AllValues;
import main.com.jkozlowska.eightwords.model.Board;
import main.com.jkozlowska.eightwords.model.Conditions;
import main.com.jkozlowska.eightwords.model.commands.AddValueCommand;
import main.com.jkozlowska.eightwords.model.commands.CommandManager;
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

public class BoardCreator extends Scene {
    private boolean isPasswordCellStage = false;
    private BorderPane root = new BorderPane();
    private GridPane gridPane = new GridPane();
    private int numberOfPasswordCells = 0;
    private int indexOfLetters = 0;
    private StackPane[][] square;
    private String value = null;
    private static Stage stage;
    private Board gameBoard;
    private String password;
    private char[] letters;
    private int boardSize;
    private VBox vBox;

    public BoardCreator(BorderPane root) {
        this(root,AllValues.windowWidth,AllValues.windowHeight);
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

        nextButton.setStyle("-fx-background-color:"+ AllValues.COLOR1 +"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");
        label.setStyle("-fx-font-size:23; -fx-text-fill:"+AllValues.COLOR1+"; -fx-font-weight: bold;");
        startButton.setStyle("-fx-background-color:"+AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");
        nextButton2.setStyle("-fx-background-color:"+AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Rectangle rectangle = new Rectangle((560-(10*gameBoard.getSize()+10))/gameBoard.getSize(),(560-(10*gameBoard.getSize()+10))/gameBoard.getSize());
                Text text = new Text();
                text.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
                square[i][j] = new StackPane();
                int finalI = i;
                int finalJ = j;

                text.setStyle("-fx-control-inner-background:"+AllValues.COLOR3);
                rectangle.setFill(Color.web(AllValues.COLOR3));
                rectangle.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
                square[i][j].getChildren().addAll(rectangle,text);

                square[i][j].getChildren().forEach(item -> { //plansza - dodanie wartosci i pól na hasło
                    item.setOnMouseClicked(mouseEvent -> {

                        if(mouseEvent.getClickCount() == 1) {
                            if(isPasswordCellStage) {
                                if(!gameBoard.isFilledCell(finalI,finalJ)) {
                                    if(gameBoard.isPasswordCell(finalI,finalJ)) {
                                        gameBoard.setPasswordCell(finalI, finalJ, false);
                                        rectangle.setFill(Color.web(AllValues.COLOR3));
                                        square[finalI][finalJ].getChildren().clear();
                                        square[finalI][finalJ].getChildren().add(rectangle);
                                        numberOfPasswordCells--;
                                    } else {
                                        gameBoard.setPasswordCell(finalI,finalJ,true);
                                        rectangle.setFill(Color.web(AllValues.COLOR1));
                                        square[finalI][finalJ].getChildren().clear();
                                        square[finalI][finalJ].getChildren().add(rectangle);
                                        numberOfPasswordCells++;
                                    }
                                }
                            } else {
                                if(value!=null) {
                                    Text text1 = new Text(value);
                                    char character = value.charAt(0);
                                    commandManager.execute(new AddValueCommand(gameBoard, finalI, finalJ, character));

                                    if(Conditions.isValidMove(gameBoard,letters,character,boardSize)) {
                                        if(gameBoard.getValue(finalI,finalJ)!=' ') {
                                            square[finalI][finalJ].getChildren().remove(1);
                                        }

                                        gameBoard.setValue(finalI, finalJ,character);
                                        square[finalI][finalJ].getChildren().add(text1);
                                    } else {
                                        commandManager.undo();
                                    }
                                }
                            }
                        }
                    });
                });

                gridPane.add(square[i][j],j,i);
            }
        }

        VBox.setMargin(hBox,new Insets(35,5,0,0));
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setStyle("-fx-background-color:"+AllValues.COLOR2);
        hBox.setSpacing(10);

        for (int i = 0; i < boardSize; i++) {
            Button button = new Button(letters[i]+"");

            hBox.getChildren().add(button);
            button.setStyle("-fx-background-color:"+ AllValues.COLOR1+"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");
            button.setPrefSize(100,50);
            button.setOnAction(event -> value = button.getText());
        }

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
                        if(textField.getText().length()==numberOfPasswordCells && Conditions.containsString(textField.getText().toUpperCase(),letters)) {
                            password = textField.getText().toUpperCase();
                            gameBoard.setPassword(password);
                            vBox.getChildren().clear();
                            hBox.getChildren().clear();

                            OwnBoard.setStage(stage);
                            stage.setScene(new OwnBoard(new BorderPane(),gameBoard));
                        }
                    }
                });

                vBox.setAlignment(Pos.TOP_CENTER);
                vBox.setSpacing(20);
                vBox.getChildren().addAll(label, textField);
            }
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(0,10,0,10));
        vBox.getChildren().addAll(hBox,nextButton);

        gridPane.setPadding(new Insets(10,10,10,20));
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color:"+AllValues.COLOR2);

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
        vBox.setStyle("-fx-background-color:"+AllValues.COLOR2);
        hBox.setStyle("-fx-background-color:"+AllValues.COLOR2);
        text.setStyle("-fx-font-size:30px; -fx-text-fill:"+ AllValues.COLOR1 +"; -fx-font-weight: bold;");

        for(int i = 4; i < 10; i++) {            //przyciski do wyboru rozmiaru planszy
           Button button = new Button(i+"x"+i);
           hBox.getChildren().add(button);
           button.setPrefSize(80, 40);
           button.setStyle("-fx-background-color:"+ AllValues.COLOR1 +"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");
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
            gameBoard = new Board(boardSize);
            letters = new char[boardSize];
            vBox.getChildren().clear();
            createPlaceForCharacter();
        });
    }

    private void createPlaceForCharacter() {
        if(indexOfLetters<boardSize) {
            Label label = new Label("Enter the "+(indexOfLetters+1)+". allowed character: ");
            TextField textField = new TextField();
            HBox hBox = new HBox();

            textField.setStyle("-fx-background-color:"+ AllValues.COLOR1 +"; -fx-text-fill:"+AllValues.COLOR2+"; -fx-font-weight: bold; -fx-font-size: 15px;");
            textField.setPrefSize(80,40);
            vBox.getChildren().clear();
            hBox.setStyle("-fx-background-color:"+AllValues.COLOR2);
            label.setStyle("-fx-font-size:30px; -fx-text-fill:"+ AllValues.COLOR1 +"; -fx-font-weight: bold;");
            vBox.getChildren().add(label);

            textField.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
                String newText = change.getControlNewText();
                if (newText.length() > 1) {
                    return null;
                } else {
                    return change;
                }
            }));

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
                if (!Conditions.checkLetter(character,letters)) {
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
            }
        });
    }

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
}
