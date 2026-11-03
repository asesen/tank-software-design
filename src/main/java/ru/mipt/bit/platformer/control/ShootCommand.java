package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Tank;

public class ShootCommand implements Command {

    private final Tank tank;

    public ShootCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        tank.shoot();
    }
}
