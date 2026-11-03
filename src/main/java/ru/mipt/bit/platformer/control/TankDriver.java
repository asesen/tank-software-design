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

    public Command plan() {
        return strategy.decide(tank, occupancy);
    }

    public void act() {
        plan().execute();
    }

    public void update(float deltaTime) {
        tank.update(deltaTime);
    }

    public Tank getTank() {
        return tank;
    }
}
