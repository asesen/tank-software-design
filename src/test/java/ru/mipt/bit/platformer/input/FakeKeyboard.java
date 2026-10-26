package ru.mipt.bit.platformer.input;

import java.util.HashSet;
import java.util.Set;

public class FakeKeyboard implements Keyboard {

    private final Set<Integer> pressed = new HashSet<Integer>();

    public void press(int keyCode) {
        pressed.add(keyCode);
    }

    @Override
    public boolean isPressed(int keyCode) {
        return pressed.contains(keyCode);
    }
}
