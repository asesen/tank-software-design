package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.TankOccupancy;
import ru.mipt.bit.platformer.model.Tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomTankPlacementTest {

    @Test
    public void placesDistinctTanksOnFreeCells() {
        final Occupancy trees = new Field(new Tree(new GridPoint2(0, 0)));
        final Occupancy player = new TankOccupancy(Arrays.asList(new Tank(new GridPoint2(1, 1))));
        Occupancy blocked = new Occupancy() {
            @Override
            public boolean isOccupied(GridPoint2 cell) {
                return trees.isOccupied(cell) || player.isOccupied(cell);
            }
        };

        List<GridPoint2> cells = new RandomTankPlacement(4, 3, 3, new Random(2)).place(blocked);

        assertEquals(3, cells.size());
        Set<GridPoint2> unique = new HashSet<GridPoint2>(cells);
        assertEquals(3, unique.size());
        for (GridPoint2 cell : cells) {
            assertTrue(cell.x >= 0 && cell.x < 4);
            assertTrue(cell.y >= 0 && cell.y < 3);
            assertFalse(blocked.isOccupied(cell));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsMoreTanksThanFreeCells() {
        Occupancy blocked = new Occupancy() {
            @Override
            public boolean isOccupied(GridPoint2 cell) {
                return false;
            }
        };
        new RandomTankPlacement(2, 2, 5, new Random(1)).place(blocked);
    }
}
