package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {

    private final GridPoint2 playerStart;
    private final List<Tree> trees;

    public Level(GridPoint2 playerStart, List<Tree> trees) {
        this.playerStart = new GridPoint2(playerStart);
        this.trees = Collections.unmodifiableList(new ArrayList<Tree>(trees));
    }

    public GridPoint2 getPlayerStart() {
        return new GridPoint2(playerStart);
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public Field toField() {
        return new Field(trees.toArray(new Tree[0]));
    }
}
