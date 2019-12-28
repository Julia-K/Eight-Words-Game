package com.jkozlowska.eightwords;

import java.util.Scanner;

public class TemporaryHelper {
    Scanner reader;
    String wartosc = "";

    public TemporaryHelper() {
        reader = new Scanner(System.in);
    }

    public void podaj(Board board) {
        while(wartosc!=null || !reader.equals("end")) {
            System.out.println("Podaj rzad");
            int row = reader.nextInt();
            System.out.println("Podaj kolumne");
            int col = reader.nextInt();
            System.out.println("Podaj wartosc");
            String wartosc = reader.next();
            char character = wartosc.toUpperCase().charAt(0);
            board.setValue(row-1,col-1,character);
            if(!Conditions.checkBoard(board,character)) {
                board.setValue(row-1,col-1,' ');
            }
            if(board.areFilledAll()) {
                wyswietl(board);
                System.out.println("WYGRALES KONIEC");
                break;
            }

            wyswietl(board);
        }
    }

    public void wyswietl(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("|"+board.getValue(i,j));
            }
            System.out.println("|");
        }
    }
}
