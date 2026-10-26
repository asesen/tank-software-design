package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextLevelSourceTest {

    private static final String LEVEL = ""
            + "___T__T___\n"
            + "__TT__TTTT\n"
            + "_________T\n"
            + "TTTT__T__T\n"
            + "_____X____\n"
            + "__________\n";

    @Test
    public void readsTreesAndPlayerFromTopRow() {
        Level level = new TextLevelSource(LEVEL, 10, 8).load();

        assertEquals(new GridPoint2(5, 3), level.getPlayerStart());
        assertEquals(15, level.getTrees().size());
        assertTrue(level.toField().isOccupied(new GridPoint2(3, 7)));
        assertTrue(level.toField().isOccupied(new GridPoint2(9, 5)));
        assertFalse(level.toField().isOccupied(level.getPlayerStart()));
        assertFalse(level.toField().isOccupied(new GridPoint2(0, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsUnknownCell() {
        new TextLevelSource("X?\n", 2, 2).load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsLevelWithoutPlayer() {
        new TextLevelSource("___\nT__\n", 3, 2).load();
    }
}
