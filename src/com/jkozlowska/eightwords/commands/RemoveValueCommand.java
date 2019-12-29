package com.jkozlowska.eightwords.commands;

import com.jkozlowska.eightwords.Board;

public class RemoveValueCommand implements Command {
    private Board board;
    private int row;
    private int col;
    private final char emptyValue = ' ';
    private char prevValue;

    public RemoveValueCommand(Board board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.prevValue = board.getValue(row-1,col-1);
    }

    public void execute() {
        board.setValue(row-1,col-1,' ');
    }

    public void undo() {
        board.setValue(row-1, col-1, prevValue);
    }

    public void redo() {
        execute();
    }

}
