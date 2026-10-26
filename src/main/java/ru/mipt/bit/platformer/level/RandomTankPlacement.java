package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Occupancy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomTankPlacement {

    private final int width;
    private final int height;
    private final int tankCount;
    private final Random random;

    public RandomTankPlacement(int width, int height, int tankCount, Random random) {
        this.width = width;
        this.height = height;
        this.tankCount = tankCount;
        this.random = random;
    }

    public List<GridPoint2> place(Occupancy blocked) {
        List<GridPoint2> freeCells = new ArrayList<GridPoint2>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                GridPoint2 cell = new GridPoint2(x, y);
                if (!blocked.isOccupied(cell)) {
                    freeCells.add(cell);
                }
            }
        }
        if (tankCount > freeCells.size()) {
            throw new IllegalArgumentException("Not enough free cells for " + tankCount + " tanks");
        }
        Collections.shuffle(freeCells, random);
        return new ArrayList<GridPoint2>(freeCells.subList(0, tankCount));
    }
}
