package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Arrays;
import java.util.List;

public class AnyOccupancy implements Occupancy {

    private final List<Occupancy> sources;

    public AnyOccupancy(Occupancy... sources) {
        this.sources = Arrays.asList(sources);
    }

    @Override
    public boolean isOccupied(GridPoint2 cell) {
        for (Occupancy source : sources) {
            if (source.isOccupied(cell)) {
                return true;
            }
        }
        return false;
    }
}
