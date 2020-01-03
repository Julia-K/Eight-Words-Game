package com.jkozlowska.eightwords.commands;

import com.jkozlowska.eightwords.Board;

import java.io.Serializable;

public class AddValueCommand implements Command, Serializable {
        private Board board;
        private int row;
        private int col;
        private char value;
        private char previousValue;

        public AddValueCommand(Board board, int row, int col, char value) {
            this.board = board;
            this.row = row;
            this.col = col;
            this.value = value;
            this.previousValue = board.getValue(row,col);
        }

        public void execute() {
            board.setValue(row,col,value);
        }

        public void undo() {
            board.setValue(row, col, previousValue);
            board.setFilledCell(row,col,false);
        }

        public void redo() {
            execute();
        }
}
