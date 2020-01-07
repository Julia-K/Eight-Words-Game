package com.jkozlowska.eightwords;

import com.jkozlowska.eightwords.commands.AddValueCommand;
import com.jkozlowska.eightwords.commands.CommandManager;

import java.io.IOException;
import java.util.Scanner;

public class TemporaryHelper {
    ReadBoard readBoard = new ReadBoard("exampleBoard.txt");
    private final static char[] letters = {'O', 'S', 'I', 'E', 'M', 'L', 'T', 'R'};
    Board board = readBoard.getGameBoard();
    CommandManager commandManager = new CommandManager();
    Scanner reader;
    String wartosc = "";

    public TemporaryHelper() throws IOException {
        reader = new Scanner(System.in);
    }

    public void podaj() {
        while(wartosc!=null || !wartosc.equals("end")) {
            System.out.println("Podaj rzad");
            int row = reader.nextInt();
            System.out.println("Podaj kolumne");
            int col = reader.nextInt();
            System.out.println("Podaj wartosc");
            String wartosc = reader.next();
            char character = wartosc.toUpperCase().charAt(0);
            if(wartosc.equals("undo")) {
                commandManager.undo();
            } else if (wartosc.equals("redo")){
                commandManager.redo();
            } else {
                commandManager.execute(new AddValueCommand(board,row,col,character));
                if(!Conditions.isValidMove(board,letters,character)) {
                    System.out.println(character+ " nie moze byc w tym miejscu");
                    commandManager.undo();
                }
                if(board.areFilledAll()) {
                    wyswietl();
                    System.out.println("WYGRALES KONIEC");
                    break;
                }
            }


            wyswietl();
        }

    }

    public void wyswietl() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("|"+board.getValue(i,j));
            }
            System.out.println("|");
        }
    }
}
