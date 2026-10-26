package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

public class TankDriver {

    private final Tank tank;
    private final ControlStrategy strategy;
    private final Occupancy occupancy;

    public TankDriver(Tank tank, ControlStrategy strategy, Occupancy occupancy) {
        this.tank = tank;
        this.strategy = strategy;
        this.occupancy = occupancy;
    }

    public void act() {
        strategy.decide(tank, occupancy).execute();
    }

    public void update(float deltaTime) {
        tank.update(deltaTime);
    }

    public Tank getTank() {
        return tank;
    }
}
