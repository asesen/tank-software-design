package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.control.KeyboardControlStrategy;
import ru.mipt.bit.platformer.control.TankDriver;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class MovementInputHandler implements InputHandler {

    private final TankDriver driver;

    public MovementInputHandler(Tank tank, Occupancy occupancy, Keyboard keyboard, List<KeyDirectionBinding> bindings) {
        this.driver = new TankDriver(tank, new KeyboardControlStrategy(keyboard, bindings), occupancy);
    }

    @Override
    public void handle() {
        driver.act();
    }
}
