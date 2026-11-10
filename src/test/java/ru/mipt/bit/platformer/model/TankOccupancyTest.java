package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TankOccupancyTest {

    @Test
    public void movingTankBlocksBothCellsForAnotherTank() {
        Tank moving = new Tank(new GridPoint2(2, 2));
        Tank other = new Tank(new GridPoint2(4, 2));
        Occupancy world = new TankOccupancy(Arrays.asList(moving, other));
        moving.tryMove(Direction.RIGHT, world);

        other.tryMove(Direction.LEFT, world);

        assertEquals(new GridPoint2(3, 2), moving.getDestination());
        assertEquals(new GridPoint2(4, 2), other.getDestination());
        assertEquals(Direction.LEFT, other.getDirection());
    }

    @Test
    public void secondTankCannotEnterCellTakenInTheSameTurn() {
        Tank first = new Tank(new GridPoint2(1, 1));
        Tank second = new Tank(new GridPoint2(3, 1));
        Occupancy world = new TankOccupancy(Arrays.asList(first, second));

        first.tryMove(Direction.RIGHT, world);
        second.tryMove(Direction.LEFT, world);

        assertEquals(new GridPoint2(2, 1), first.getDestination());
        assertEquals(new GridPoint2(3, 1), second.getDestination());
        assertTrue(first.occupies(new GridPoint2(2, 1)));
    }

    @Test
    public void cellBehindMovingTankStaysBlockedUntilArrival() {
        Tank moving = new Tank(new GridPoint2(2, 2));
        Tank follower = new Tank(new GridPoint2(1, 2));
        Occupancy world = new AnyOccupancy(
                new Field(new Tree(new GridPoint2(5, 2))),
                new LevelBounds(10, 8),
                new TankOccupancy(Arrays.asList(moving, follower))
        );
        moving.tryMove(Direction.RIGHT, world);

        follower.tryMove(Direction.RIGHT, world);

        assertEquals(new GridPoint2(1, 2), follower.getDestination());
        assertEquals(Direction.RIGHT, follower.getDirection());
        assertTrue(world.isOccupied(new GridPoint2(2, 2)));
        assertTrue(world.isOccupied(new GridPoint2(5, 2)));
        assertTrue(world.isOccupied(new GridPoint2(-1, 2)));

        moving.update(0.4f);
        follower.tryMove(Direction.RIGHT, world);

        assertEquals(new GridPoint2(2, 2), follower.getDestination());
        assertFalse(moving.occupies(new GridPoint2(2, 2)));
    }
}
