package com.jkozlowska.eightwords;

public class Conditions {

    private static boolean status = true;
    private final static char[] eightLetters = {'O', 'S', 'I', 'E', 'M', 'L', 'T', 'R'};

    public Conditions() {}

    public static boolean checkBoard(Board board, char letter) {
        if (checkLetter(letter) && checkRows(board) && checkColumns(board) && checkDiagonal(board,firstDiagonal(board)) && checkDiagonal(board,secondDiagonal(board))) {
            return true;
        }
        return false;
    }
    public static boolean checkRows(Board board) {
        for (int i = 0; i < 8; i++) {
            OneCell[] rowHolder = board.getRow(i);

            for (int j = 0; j < 8; j++) {
                char character = rowHolder[j].getValue();
                if(!contains(eightLetters,character)) {
                    if(!(rowHolder[j].getValue()==' ')) {
                        //setError(i,j);
                        return false;
                    }
                } else {
                    for (int x = j+1; x < 8; x++) {
                        if(character == rowHolder[x].getValue()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkColumns(Board board) {
        Board rotateBoard = new Board(8);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                rotateBoard.setValue(i,j,board.getValue(j,i));
            }
        }

        return checkRows(rotateBoard);
    }

    public static char[] firstDiagonal(Board board) {
        char[] diagonal = new char[8];
        for (int i = 0; i < 8; i++) {
            diagonal[i] = board.getValue(i,i);
        }

        return diagonal;
    }

    public static char[] secondDiagonal(Board board) {
        char[] diagonal = new char[8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((i+j) == (board.getSize()-1)) {
                    diagonal[i] = board.getValue(i,j);
                }
            }
        }
        return diagonal;
    }

    public static boolean checkDiagonal(Board board,char[] diagonal) {
        for (int i = 0; i < 8; i++) {
            if(!contains(eightLetters,diagonal[i])) {
                if(!(diagonal[i]==' ')) {
                    //setError(i,i);
                    status = false;
                    return status;
                }
            } else {
                for (int x = i + 1; x < 8; x++) {
                    if (diagonal[i] == diagonal[x]) {
                        return false;
                    }
                }
            }
        }
        return status;
    }

    public static boolean contains(char[] charArray, char letter) {
        boolean result = false;

        for (char c : charArray) {
            if(c == letter) {
                result = true;
            }
        }

        return result;
    }

    public static boolean checkLetter(char letter) {
        return contains(eightLetters,letter);
    }
}
