package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(90f, 0, 1),
    LEFT(-180f, -1, 0),
    DOWN(-90f, 0, -1),
    RIGHT(0f, 1, 0);

    private final float rotation;
    private final GridPoint2 vector;

    Direction(float rotation, int deltaX, int deltaY) {
        this.rotation = rotation;
        this.vector = new GridPoint2(deltaX, deltaY);
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getVector() {
        return new GridPoint2(vector);
    }

    public GridPoint2 addDirection(GridPoint2 cell) {
        return new GridPoint2(cell).add(vector);
    }
}
