package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(90f, 0, 1),
    LEFT(-180f, -1, 0),
    DOWN(-90f, 0, -1),
    RIGHT(0f, 1, 0);

    private final float rotation;
    private final int deltaX;
    private final int deltaY;

    Direction(float rotation, int deltaX, int deltaY) {
        this.rotation = rotation;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 neighbor(GridPoint2 cell) {
        return new GridPoint2(cell).add(deltaX, deltaY);
    }
}
