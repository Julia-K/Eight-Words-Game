package com.jkozlowska.eightwords.commands;

import com.jkozlowska.eightwords.model.Board;

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
            if(previousValue == ' ') {
                board.setFilledCell(row,col,false);
            } else {
                board.setFilledCell(row,col,true);
            }
        }

        public void redo() {
            execute();
        }
}
