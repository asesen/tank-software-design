package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LevelBoundsTest {

    private final LevelBounds bounds = new LevelBounds(10, 8);

    @Test
    public void cellsOutsideMapAreOccupied() {
        assertFalse(bounds.isOccupied(new GridPoint2(0, 0)));
        assertFalse(bounds.isOccupied(new GridPoint2(9, 7)));
        assertTrue(bounds.isOccupied(new GridPoint2(-1, 0)));
        assertTrue(bounds.isOccupied(new GridPoint2(0, -1)));
        assertTrue(bounds.isOccupied(new GridPoint2(10, 0)));
        assertTrue(bounds.isOccupied(new GridPoint2(0, 8)));
    }

    @Test
    public void tankTurnsAtMapEdgeWithoutLeaving() {
        Tank tank = new Tank(new GridPoint2(0, 0));

        tank.tryMove(Direction.LEFT, bounds);

        assertEquals(new GridPoint2(0, 0), tank.getCell());
        assertEquals(new GridPoint2(0, 0), tank.getDestination());
        assertEquals(Direction.LEFT, tank.getDirection());
    }
}
