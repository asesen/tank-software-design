package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.graphics.HealthDisplay;
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
    private final int healthKey;
    private final HealthDisplay healthDisplay;
    private boolean healthKeyWasDown;

    public KeyboardControlStrategy(Keyboard keyboard, List<KeyDirectionBinding> bindings) {
        this(keyboard, bindings, -1, -1, null);
    }

    public KeyboardControlStrategy(Keyboard keyboard, List<KeyDirectionBinding> bindings, int shootKey) {
        this(keyboard, bindings, shootKey, -1, null);
    }

    public KeyboardControlStrategy(Keyboard keyboard, List<KeyDirectionBinding> bindings, int shootKey,
                                   int healthKey, HealthDisplay healthDisplay) {
        this.keyboard = keyboard;
        this.bindings = bindings;
        this.shootKey = shootKey;
        this.healthKey = healthKey;
        this.healthDisplay = healthDisplay;
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
        if (healthDisplay != null && healthKey >= 0) {
            boolean pressed = keyboard.isPressed(healthKey);
            if (pressed && !healthKeyWasDown) {
                commands.add(new ToggleHealthDisplayCommand(healthDisplay));
            }
            healthKeyWasDown = pressed;
        }
        return new CompositeCommand(commands);
    }
}
