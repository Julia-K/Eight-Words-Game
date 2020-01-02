package com.jkozlowska.eightwords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadBoard {
    private Board board = new Board(8);

    public ReadBoard(String pathname) throws IOException {
        BufferedReader txtReader = new BufferedReader(new FileReader("exampleBoard.txt"));
        String stich;
        String[] lineHolder = null;
        try {
            for (int i = 0; i < 8; i++) {
                stich = txtReader.readLine();
                lineHolder = stich.split("");
                for (int j = 0; j < 8; j++) {
                    stich = lineHolder[j];
                    if(stich.equals("0")) {
                        board.setValue(i,j,' ');
                    } else {
                        char character = stich.charAt(0);
                        board.setValue(i,j,character);
                        board.setChangePossibilityCell(i,j,false);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot read text file.");
        }
    }

    /////////////////////////
    //TODO: ZMIENIC HELPERA I UNDO ZEBY NIE ZAPISYWAL NIEUDANYCH PLIKOW
    //TODO:
    //TODO: ZAPIS DO PLIKU, WCZYTYWANIE
    ////////////////////////

    public Board getGameBoard() {
        return this.board;
    }
}

