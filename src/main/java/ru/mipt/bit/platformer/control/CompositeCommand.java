package ru.mipt.bit.platformer.control;

import java.util.ArrayList;
import java.util.List;

public class CompositeCommand implements Command {

    private final List<Command> commands;

    public CompositeCommand(List<Command> commands) {
        this.commands = new ArrayList<Command>(commands);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
}
