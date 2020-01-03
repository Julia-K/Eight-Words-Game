package com.jkozlowska.eightwords;

import com.jkozlowska.eightwords.commands.AddValueCommand;
import com.jkozlowska.eightwords.commands.CommandManager;

import java.io.Serializable;

public class Board implements CellFunctions, Serializable {
    private final int size;
    private OneCell[][] initialBoard;
    private CommandManager commandManager = new CommandManager();
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

    public boolean isCellChangePossible(int row, int col) {
        return board[row][col].getChangePossibility();
    }

    public void setCellChangeToPossible(int row, int col, boolean changePossibility) {
        board[row][col].setChangePossibility(changePossibility);
    }

    public void setPasswordCell(int row, int col, boolean pswNeeded) {
        board[row][col].setPasswordNeeded(pswNeeded);
    }

    public boolean isPasswordCell(int row, int col) {
        return board[row][col].isPasswordNeeded();
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

    public void setFilledCell(int row, int col,Boolean bool) {
        board[row][col].setFilled(bool);
    }

    public void setValue(final int row, final int col, final char value) {
        board[row][col].setValue(value);
    }

    public void setInitialBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                initialBoard[i][j].setValue(board[i][j].getValue());
            }
        }
        restart();
    }

    public OneCell[][] getInitialBoard() {
        restart();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setValue(initialBoard[i][j].getValue());
            }
        }
        return board;
    }

    public void addValueWithHistory(int row, int col, char value) {
        commandManager.execute(new AddValueCommand(this, row, col, value));
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }
}
