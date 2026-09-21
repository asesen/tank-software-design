package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputHandlerTest {

    private static final int UP_KEY = 1;

    @Test
    public void movementHandlerMovesTankOnPressedKey() {
        Tank tank = new Tank(new GridPoint2(1, 1));
        Field field = new Field(new Tree(new GridPoint2(1, 3)));
        FakeKeyboard keyboard = new FakeKeyboard();
        keyboard.press(UP_KEY);
        MovementInputHandler handler = new MovementInputHandler(
                tank,
                field,
                keyboard,
                Arrays.asList(new KeyDirectionBinding(Direction.UP, UP_KEY))
        );

        handler.handle();

        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test
    public void compositeHandlerInvokesEveryHandler() {
        AtomicBoolean firstCalled = new AtomicBoolean(false);
        AtomicBoolean secondCalled = new AtomicBoolean(false);
        InputHandler composite = new CompositeInputHandler(
                new InputHandler() {
                    @Override
                    public void handle() {
                        firstCalled.set(true);
                    }
                },
                new InputHandler() {
                    @Override
                    public void handle() {
                        secondCalled.set(true);
                    }
                }
        );

        composite.handle();

        assertTrue(firstCalled.get());
        assertTrue(secondCalled.get());
    }
}
