package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Field {

    private final List<Tree> trees;

    public Field(Tree... trees) {
        this.trees = Collections.unmodifiableList(Arrays.asList(trees));
    }

    public boolean isOccupied(GridPoint2 cell) {
        for (Tree tree : trees) {
            if (tree.occupies(cell)) {
                return true;
            }
        }
        return false;
    }
}
