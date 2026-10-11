package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLevelSource implements LevelSource {

    private final int width;
    private final int height;
    private final int obstacleCount;
    private final Random random;

    public RandomLevelSource(int width, int height, int obstacleCount, Random random) {
        this.width = width;
        this.height = height;
        this.obstacleCount = obstacleCount;
        this.random = random;
    }

    @Override
    public Level load() {
        int cellCount = width * height;
        if (obstacleCount < 0 || obstacleCount >= cellCount) {
            throw new IllegalArgumentException("Obstacle count must leave a free cell for the player");
        }
        List<GridPoint2> cells = new ArrayList<GridPoint2>(cellCount);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells.add(new GridPoint2(x, y));
            }
        }
        Collections.shuffle(cells, random);
        GridPoint2 playerStart = cells.remove(0);
        List<Tree> trees = new ArrayList<Tree>(obstacleCount);
        for (int i = 0; i < obstacleCount; i++) {
            trees.add(new Tree(cells.remove(0)));
        }
        return new Level(playerStart, trees);
    }
}
