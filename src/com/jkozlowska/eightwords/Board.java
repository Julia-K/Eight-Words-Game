package com.jkozlowska.eightwords;

public class Board implements CellFunctions {
    private final int size;
    //private int squareSize;
    private OneCell[][] board;

    public Board(final int size) {
        this.size = size;
        board = new OneCell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new OneCell();
            }
        }
    }

    public OneCell[] getRow(int row)  {
        OneCell[] tempRow = new OneCell[size];
        for (int i = 0; i < size; i++) {
            tempRow[i] = board[row][i];
        }
        return tempRow;
    }

    public boolean areFilledAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!board[i][j].isFilled())
                    return false;
            }
        }
        return true;
    }

    public void setChangePossibilityCell(int row, int col, boolean changePossibility) {
        board[row][col].setChangePossibility(changePossibility);
    }

    public void setPasswordNeededCell(int row, int col, boolean pswNeeded) {
        board[row][col].setPasswordNeeded(pswNeeded);
    }

    public boolean getPasswordNeededCell(int row, int col) {
        return board[row][col].getPasswordNeeded();
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].clear();
            }
        }
    }

    @Override
    public void restart() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].restart();
            }
        }
    }

    @Override
    public boolean isFilled() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!board[i][j].isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isFilledCell(final int row, final int col) {
        return board[row][col].isFilled();
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfCells() {
        return size*size;
    }

    public void clearCell(final int row, final int col) {
        board[row][col].clear();
    }

    public void restartCell(final int row, final int col) {
        board[row][col].restart();
    }

    public char getValue(final int row, final int col) {
        return  board[row][col].getValue();
    }

    public void setValue(final int row, final int col, final char value) {
        board[row][col].setValue(value);
    }

}
