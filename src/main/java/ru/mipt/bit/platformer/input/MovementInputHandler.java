package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class MovementInputHandler implements InputHandler {

    private final Tank tank;
    private final Occupancy occupancy;
    private final Keyboard keyboard;
    private final List<KeyDirectionBinding> bindings;

    public MovementInputHandler(Tank tank, Occupancy occupancy, Keyboard keyboard, List<KeyDirectionBinding> bindings) {
        this.tank = tank;
        this.occupancy = occupancy;
        this.keyboard = keyboard;
        this.bindings = bindings;
    }

    @Override
    public void handle() {
        for (KeyDirectionBinding binding : bindings) {
            if (binding.isPressed(keyboard)) {
                tank.tryMove(binding.getDirection(), occupancy);
            }
        }
    }
}
