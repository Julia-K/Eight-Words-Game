package main.com.jkozlowska.eightwords.model;

public class Conditions {
    private static boolean status = true;

    public static boolean isValidMove(Board board, char[] letters, char letter, int boardSize) {
        return checkLetter(letter, letters) && checkRows(board, letters, boardSize) && checkColumns(board, letters, boardSize) && checkDiagonal(firstDiagonal(board, boardSize), letters, boardSize) && checkDiagonal(secondDiagonal(board, boardSize), letters, boardSize);
    }

    public static boolean checkRows(Board board, char[] letters, int boardSize) {
        for (int i = 0; i < boardSize; i++) {
            OneCell[] rowHolder = board.getRow(i);

            for (int j = 0; j < boardSize; j++) {
                char character = rowHolder[j].getValue();
                if(!checkLetter(character,letters)) {
                    if(!(rowHolder[j].getValue()==' ')) {
                        return false;
                    }
                } else {
                    for (int x = j+1; x < boardSize; x++) {
                        if(character == rowHolder[x].getValue()) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean containsString(String word, char[] letters) {
        for (int i = 0; i < word.length(); i++) {
            if(!checkLetter(word.charAt(i),letters)) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkColumns(Board board, char[] letters, int boardSize) {
        Board rotateBoard = new Board(boardSize);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                rotateBoard.setValue(i,j,board.getValue(j,i));
            }
        }

        return checkRows(rotateBoard,letters, boardSize);
    }

    public static char[] firstDiagonal(Board board, int boardSize) {
        char[] diagonal = new char[boardSize];

        for (int i = 0; i < boardSize; i++) {
            diagonal[i] = board.getValue(i,i);
        }

        return diagonal;
    }

    public static char[] secondDiagonal(Board board, int boardSize) {
        char[] diagonal = new char[boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if((i+j) == (board.getSize()-1)) {
                    diagonal[i] = board.getValue(i,j);
                }
            }
        }

        return diagonal;
    }

    public static boolean checkDiagonal(char[] diagonal, char[] letters, int boardSize) {
        for (int i = 0; i < boardSize; i++) {
            if(!checkLetter(diagonal[i],letters)) {
                if(!(diagonal[i]==' ')) {
                    return status = false;
                }
            } else {
                for (int x = i + 1; x < boardSize; x++) {
                    if (diagonal[i] == diagonal[x]) {
                        return false;
                    }
                }
            }
        }

        return status;
    }

    public static boolean checkLetter(char letter, char[] letters) {
        boolean result = false;

        for (char c : letters) {
            if(c == letter) {
                result = true;
            }
        }

        return result;
    }
}
