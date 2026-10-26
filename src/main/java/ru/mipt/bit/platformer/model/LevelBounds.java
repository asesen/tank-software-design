package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class LevelBounds implements Occupancy {

    private final int width;
    private final int height;

    public LevelBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isOccupied(GridPoint2 cell) {
        return cell.x < 0 || cell.y < 0 || cell.x >= width || cell.y >= height;
    }
}
