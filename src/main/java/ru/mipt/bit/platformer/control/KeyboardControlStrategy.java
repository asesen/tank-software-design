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
    private final int shootKey;

    public KeyboardControlStrategy(Keyboard keyboard, List<KeyDirectionBinding> bindings) {
        this(keyboard, bindings, -1);
    }

    public KeyboardControlStrategy(Keyboard keyboard, List<KeyDirectionBinding> bindings, int shootKey) {
        this.keyboard = keyboard;
        this.bindings = bindings;
        this.shootKey = shootKey;
    }

    @Override
    public Command decide(Tank tank, Occupancy occupancy) {
        List<Command> commands = new ArrayList<Command>();
        for (KeyDirectionBinding binding : bindings) {
            if (binding.isPressed(keyboard)) {
                commands.add(new MoveCommand(tank, binding.getDirection(), occupancy));
            }
        }
        if (shootKey >= 0 && keyboard.isPressed(shootKey)) {
            commands.add(new ShootCommand(tank));
        }
        return new CompositeCommand(commands);
    }
}
