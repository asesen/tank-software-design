package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
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
}
