package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void storesRotationAndVector() {
        assertEquals(90f, Direction.UP.getRotation(), 0f);
        assertEquals(new GridPoint2(0, 1), Direction.UP.getVector());
        assertEquals(-180f, Direction.LEFT.getRotation(), 0f);
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getVector());
        assertEquals(-90f, Direction.DOWN.getRotation(), 0f);
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getVector());
        assertEquals(0f, Direction.RIGHT.getRotation(), 0f);
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getVector());
    }

    @Test
    public void addsVectorToCell() {
        GridPoint2 start = new GridPoint2(2, 3);
        assertEquals(new GridPoint2(2, 4), Direction.UP.addDirection(start));
        assertEquals(new GridPoint2(1, 3), Direction.LEFT.addDirection(start));
        assertEquals(new GridPoint2(2, 2), Direction.DOWN.addDirection(start));
        assertEquals(new GridPoint2(3, 3), Direction.RIGHT.addDirection(start));
        assertEquals(new GridPoint2(2, 3), start);
    }
}
