package com.example.aaaBookstoreCA.pattern.command;

// base interface for all commands
public interface Command {
    void execute(); // Executes the command
    void undo();    // Reverses the command
}