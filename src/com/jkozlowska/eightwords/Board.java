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

    public int getCellsNumber() {
        return size*size;
    }

    public void clearCell(final int row, final int col) {
        board[row][col].clear();
    }

    public void restartCell(final int row, final int col) {
        board[row][col].restart();
    }

    

}
