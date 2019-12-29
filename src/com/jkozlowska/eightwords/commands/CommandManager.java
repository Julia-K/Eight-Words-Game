package com.jkozlowska.eightwords.commands;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandManager() {
        undoStack = new Stack<Command>();
        redoStack = new Stack<Command>();
    }

    public void execute(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        assert(!undoStack.empty());
        Command cmd = undoStack.pop();
        cmd.undo();
        redoStack.push(cmd);
    }

    public void redo() {
        assert(!redoStack.empty());
        Command cmd = redoStack.pop();
        cmd.execute();
        undoStack.push(cmd);
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }
}
