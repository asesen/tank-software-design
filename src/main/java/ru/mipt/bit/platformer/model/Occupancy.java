package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface Occupancy {

    boolean isOccupied(GridPoint2 cell);
}
