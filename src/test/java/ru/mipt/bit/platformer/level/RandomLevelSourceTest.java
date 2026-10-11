package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Tree;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomLevelSourceTest {

    @Test
    public void placesPlayerOnFreeCellInsideMap() {
        Level level = new RandomLevelSource(10, 8, 12, new Random(1)).load();

        GridPoint2 start = level.getPlayerStart();
        assertEquals(12, level.getTrees().size());
        assertTrue(start.x >= 0 && start.x < 10);
        assertTrue(start.y >= 0 && start.y < 8);
        assertFalse(level.toField().isOccupied(start));
        for (Tree tree : level.getTrees()) {
            GridPoint2 cell = tree.getCell();
            assertTrue(cell.x >= 0 && cell.x < 10);
            assertTrue(cell.y >= 0 && cell.y < 8);
        }
    }

    @Test
    public void sameSeedGivesSameLevel() {
        Level first = new RandomLevelSource(4, 3, 2, new Random(7)).load();
        Level second = new RandomLevelSource(4, 3, 2, new Random(7)).load();

        assertEquals(first.getPlayerStart(), second.getPlayerStart());
        assertEquals(first.getTrees().get(0).getCell(), second.getTrees().get(0).getCell());
    }
}
