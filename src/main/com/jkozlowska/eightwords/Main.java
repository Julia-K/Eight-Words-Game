package main.com.jkozlowska.eightwords;

import main.com.jkozlowska.eightwords.view.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Eight Word Game");
        MainWindow.setStage(primaryStage);
        primaryStage.setScene(MainWindow.getMainWindow());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
