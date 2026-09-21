package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Field {

    private final List<Tree> trees;

    public Field(Tree... trees) {
        this.trees = Collections.unmodifiableList(new ArrayList<Tree>(Arrays.asList(trees)));
    }

    public boolean isOccupied(GridPoint2 cell) {
        for (Tree tree : trees) {
            if (tree.occupies(cell)) {
                return true;
            }
        }
        return false;
    }

    public List<Tree> getTrees() {
        return trees;
    }
}
