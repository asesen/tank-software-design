package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.input.KeyDirectionBinding;
import ru.mipt.bit.platformer.input.Keyboard;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class KeyboardControlStrategy implements ControlStrategy {

    private final Keyboard keyboard;
    private final List<KeyDirectionBinding> bindings;

    public KeyboardControlStrategy(Keyboard keyboard, List<KeyDirectionBinding> bindings) {
        this.keyboard = keyboard;
        this.bindings = bindings;
    }

    @Override
    public Command decide(Tank tank, Occupancy occupancy) {
        List<Command> commands = new ArrayList<Command>();
        for (KeyDirectionBinding binding : bindings) {
            if (binding.isPressed(keyboard)) {
                commands.add(new MoveCommand(tank, binding.getDirection(), occupancy));
            }
        }
        return new CompositeCommand(commands);
    }
}
