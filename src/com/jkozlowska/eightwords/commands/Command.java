package com.jkozlowska.eightwords.commands;

public interface Command {
    void execute();
    void undo();
}
