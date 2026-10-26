package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.input.FakeKeyboard;
import ru.mipt.bit.platformer.input.KeyDirectionBinding;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.LevelBounds;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ControlTest {

    private static final int UP_KEY = 1;

    private final Occupancy openMap = new LevelBounds(10, 8);

    @Test
    public void moveCommandStartsMovement() {
        Tank tank = new Tank(new GridPoint2(1, 1));

        new MoveCommand(tank, Direction.RIGHT, openMap).execute();

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    public void keyboardStrategyMovesWhenKeyIsPressed() {
        Tank tank = new Tank(new GridPoint2(1, 1));
        FakeKeyboard keyboard = new FakeKeyboard();
        keyboard.press(UP_KEY);
        ControlStrategy strategy = new KeyboardControlStrategy(
                keyboard,
                Arrays.asList(new KeyDirectionBinding(Direction.UP, UP_KEY))
        );

        strategy.decide(tank, openMap).execute();

        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test
    public void randomStrategyPicksDirectionAndDrivesWhenIdle() {
        Tank tank = new Tank(new GridPoint2(4, 4));
        ControlStrategy strategy = new RandomDirectionStrategy(new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        });
        TankDriver driver = new TankDriver(tank, strategy, openMap);

        driver.act();

        assertEquals(Direction.UP, tank.getDirection());
        assertEquals(new GridPoint2(4, 5), tank.getDestination());
        assertFalse(tank.hasReachedDestination());
    }

    @Test
    public void randomStrategyDoesNotChangeMoveWhileTankIsTravelling() {
        Tank tank = new Tank(new GridPoint2(4, 4));
        ControlStrategy strategy = new RandomDirectionStrategy(new Random() {
            private int call;

            @Override
            public int nextInt(int bound) {
                return call++;
            }
        });
        TankDriver driver = new TankDriver(tank, strategy, openMap);

        driver.act();
        driver.act();

        assertEquals(Direction.UP, tank.getDirection());
        assertEquals(new GridPoint2(4, 5), tank.getDestination());
    }
}
