package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface LevelObject {

    void live(float tick);

    boolean occupies(GridPoint2 cell);

    default void onAdded(LogicalLevel level) {
    }
}
