package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Direction;

public class KeyDirectionBinding {

    private final Direction direction;
    private final int[] keyCodes;

    public KeyDirectionBinding(Direction direction, int... keyCodes) {
        this.direction = direction;
        this.keyCodes = keyCodes;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isPressed(Keyboard keyboard) {
        for (int keyCode : keyCodes) {
            if (keyboard.isPressed(keyCode)) {
                return true;
            }
        }
        return false;
    }
}
