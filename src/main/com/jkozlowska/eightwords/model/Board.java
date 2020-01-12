package main.com.jkozlowska.eightwords.model;

import main.com.jkozlowska.eightwords.model.commands.AddValueCommand;
import main.com.jkozlowska.eightwords.model.commands.CommandManager;

import java.io.Serializable;

public class Board implements CellFunctions, Serializable {
    private CommandManager commandManager = new CommandManager();
    private final int size;
    private char[] letters;
    private String password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLetters(char[] letters) {
        this.letters = letters;
    }

    public char[] getLetters() {
        return letters;
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

    public char getValue(final int row, final int col) {
        return  board[row][col].getValue();
    }

    public void setFilledCell(int row, int col,Boolean bool) {
        board[row][col].setFilled(bool);
    }

    public void setValue(final int row, final int col, final char value) {
        if(isCellChangePossible(row,col)) {
            board[row][col].setValue(value);
        }
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
