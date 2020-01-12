package main.com.jkozlowska.eightwords.model.commands;

import java.io.Serializable;
import java.util.Stack;

public class CommandManager implements Serializable {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if(canUndo()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if(canRedo()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }
}
