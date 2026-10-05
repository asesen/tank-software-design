package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tree implements Obstacle {

    private final GridPoint2 cell;

    public Tree(GridPoint2 cell) {
        this.cell = new GridPoint2(cell);
    }

    public boolean occupies(GridPoint2 other) {
        return cell.equals(other);
    }

    public GridPoint2 getCell() {
        return new GridPoint2(cell);
    }
}
