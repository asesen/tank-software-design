package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldTest {

    @Test
    public void reportsOccupiedCells() {
        Tree first = new Tree(new GridPoint2(1, 3));
        Tree second = new Tree(new GridPoint2(4, 2));
        Field field = new Field(first, second);

        assertTrue(field.isOccupied(new GridPoint2(1, 3)));
        assertTrue(field.isOccupied(new GridPoint2(4, 2)));
        assertFalse(field.isOccupied(new GridPoint2(1, 1)));
        assertEquals(2, field.getObstacles().size());
    }

    @Test
    public void acceptsObstacleThatIsNotATree() {
        Obstacle wall = new Obstacle() {
            @Override
            public boolean occupies(GridPoint2 cell) {
                return cell.equals(new GridPoint2(2, 2));
            }
        };
        Field field = new Field(wall);

        assertTrue(field.isOccupied(new GridPoint2(2, 2)));
        assertFalse(field.isOccupied(new GridPoint2(1, 1)));
    }
}
