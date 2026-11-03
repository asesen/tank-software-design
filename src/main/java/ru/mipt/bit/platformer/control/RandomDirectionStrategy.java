package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Random;

public class RandomDirectionStrategy implements ControlStrategy {

    private final Random random;

    public RandomDirectionStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Command decide(Tank tank, Occupancy occupancy) {
        Direction[] directions = Direction.values();
        int roll = random.nextInt(directions.length + 1);
        if (roll == directions.length) {
            return new ShootCommand(tank);
        }
        return new MoveCommand(tank, directions[roll], occupancy);
    }
}
