package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Field implements Occupancy {

    private final List<Obstacle> obstacles;

    public Field(Obstacle... obstacles) {
        this.obstacles = Collections.unmodifiableList(new ArrayList<Obstacle>(Arrays.asList(obstacles)));
    }

    @Override
    public boolean isOccupied(GridPoint2 cell) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.occupies(cell)) {
                return true;
            }
        }
        return false;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
