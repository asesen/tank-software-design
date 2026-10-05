package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TankTest {

    private final Tree tree = new Tree(new GridPoint2(1, 3));
    private final Field field = new Field(tree);
    private final Tank tank = new Tank(new GridPoint2(1, 1));

    @Test
    public void startsIdleFacingRight() {
        assertEquals(new GridPoint2(1, 1), tank.getCell());
        assertEquals(new GridPoint2(1, 1), tank.getDestination());
        assertTrue(tank.hasReachedDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
        assertEquals(0f, tank.getRotation(), 0f);
    }

    @Test
    public void movesToFreeNeighbor() {
        tank.tryMove(Direction.UP, field);

        assertEquals(new GridPoint2(1, 1), tank.getCell());
        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertFalse(tank.hasReachedDestination());
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test
    public void rotatesWithoutMovingIntoTree() {
        tank.tryMove(Direction.UP, field);
        tank.update(1f);
        tank.tryMove(Direction.UP, field);

        assertEquals(new GridPoint2(1, 2), tank.getCell());
        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertTrue(tank.hasReachedDestination());
        assertEquals(Direction.UP, tank.getDirection());
        assertEquals(90f, tank.getRotation(), 0f);
    }

    @Test
    public void ignoresNewMoveWhileTravelling() {
        tank.tryMove(Direction.RIGHT, field);
        tank.tryMove(Direction.DOWN, field);

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    public void arrivesAfterFullMovementDuration() {
        tank.tryMove(Direction.RIGHT, field);
        tank.update(0.4f);

        assertTrue(tank.hasReachedDestination());
        assertEquals(new GridPoint2(2, 1), tank.getCell());
    }

    @Test
    public void movementDurationComesFromConstructor() {
        Tank fastTank = new Tank(new GridPoint2(0, 0), 0.2f);
        fastTank.tryMove(Direction.RIGHT, field);
        fastTank.update(0.2f);

        assertTrue(fastTank.hasReachedDestination());
        assertEquals(new GridPoint2(1, 0), fastTank.getCell());
    }
}
