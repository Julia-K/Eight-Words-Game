package main.com.jkozlowska.eightwords.model.commands;

public interface Command {
    void execute();
    void undo();
}
