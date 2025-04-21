package com.example.aaaBookstoreCA.pattern.command;

import java.util.Stack;

// This class executes commands and stores them for undo
public class CartInvoker {

    // Stack to keep history of executed commands (for undo)
    private Stack<Command> commandHistory = new Stack<>();

    // Execute a command and add it to history
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    // Undo the last executed command
    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        }
    }
}