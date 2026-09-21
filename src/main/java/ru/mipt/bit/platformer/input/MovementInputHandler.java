package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

public class MovementInputHandler implements InputHandler {

    private static final List<KeyDirectionBinding> DEFAULT_BINDINGS = Arrays.asList(
            new KeyDirectionBinding(Direction.UP, UP, W),
            new KeyDirectionBinding(Direction.LEFT, LEFT, A),
            new KeyDirectionBinding(Direction.DOWN, DOWN, S),
            new KeyDirectionBinding(Direction.RIGHT, RIGHT, D)
    );

    private final Tank tank;
    private final Field field;
    private final Keyboard keyboard;
    private final List<KeyDirectionBinding> bindings;

    public MovementInputHandler(Tank tank, Field field, Keyboard keyboard) {
        this(tank, field, keyboard, DEFAULT_BINDINGS);
    }

    public MovementInputHandler(Tank tank, Field field, Keyboard keyboard, List<KeyDirectionBinding> bindings) {
        this.tank = tank;
        this.field = field;
        this.keyboard = keyboard;
        this.bindings = bindings;
    }

    @Override
    public void handle() {
        for (KeyDirectionBinding binding : bindings) {
            if (binding.isPressed(keyboard)) {
                tank.tryMove(binding.getDirection(), field);
            }
        }
    }
}
