package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommand implements Command {

    private final Tank tank;
    private final Direction direction;
    private final Occupancy occupancy;

    public MoveCommand(Tank tank, Direction direction, Occupancy occupancy) {
        this.tank = tank;
        this.direction = direction;
        this.occupancy = occupancy;
    }

    @Override
    public void execute() {
        tank.tryMove(direction, occupancy);
    }
}
