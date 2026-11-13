package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.graphics.HealthDisplay;

public class ToggleHealthDisplayCommand implements Command {

    private final HealthDisplay display;

    public ToggleHealthDisplayCommand(HealthDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.toggle();
    }
}
