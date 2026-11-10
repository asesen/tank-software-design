package ru.mipt.bit.platformer.graphics;

public class HealthDisplay {

    private boolean visible;

    public void toggle() {
        visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
