package com.jkozlowska.eightwords;

public class Conditions {

    private static boolean status = true;
    private final static char[] eightLetters = {'O', 'S', 'I', 'E', 'M', 'L', 'T', 'R'};

    public Conditions() {}

    public static boolean isValidMove(Board board, char[] letters, char letter) {
        if (checkLetter(letter, letters) && checkRows(board, letters) && checkColumns(board,letters) && checkDiagonal(firstDiagonal(board),letters) && checkDiagonal(secondDiagonal(board),letters)) {
            return true;
        }
        return false;
    }
    public static boolean checkRows(Board board, char[] letters) {
        for (int i = 0; i < 8; i++) {
            OneCell[] rowHolder = board.getRow(i);

            for (int j = 0; j < 8; j++) {
                char character = rowHolder[j].getValue();
                if(!contains(letters,character)) {
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

    public static boolean checkColumns(Board board, char[] letters) {
        Board rotateBoard = new Board(8);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                rotateBoard.setValue(i,j,board.getValue(j,i));
            }
        }
        return checkRows(rotateBoard,letters);
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

    public static boolean checkDiagonal(char[] diagonal,char[] letters) {
        for (int i = 0; i < 8; i++) {
            if(!contains(letters,diagonal[i])) {
                if(!(diagonal[i]==' ')) {
                    return status = false;
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

    public static boolean checkLetter(char letter, char[] letters) {
        return contains(letters,letter);
    }

    public static boolean contains(char[] eightLetters, char letter) {
        boolean result = false;

        for (char c : eightLetters) {
            if(c == letter) {
                result = true;
            }
        }
        return result;
    }
}
