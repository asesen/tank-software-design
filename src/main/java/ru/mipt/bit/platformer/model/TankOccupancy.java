package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class TankOccupancy implements Occupancy {

    private final List<Tank> tanks;

    public TankOccupancy(List<Tank> tanks) {
        this.tanks = new ArrayList<Tank>(tanks);
    }

    @Override
    public boolean isOccupied(GridPoint2 cell) {
        for (Tank tank : tanks) {
            if (tank.occupies(cell)) {
                return true;
            }
        }
        return false;
    }
}
