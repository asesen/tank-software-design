package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextLevelSource implements LevelSource {

    private static final char EMPTY = '_';
    private static final char TREE = 'T';
    private static final char PLAYER = 'X';

    private final String text;
    private final int width;
    private final int height;

    public TextLevelSource(String text, int width, int height) {
        this.text = text;
        this.width = width;
        this.height = height;
    }

    public static TextLevelSource fromClasspath(String resourcePath, int width, int height) {
        InputStream input = TextLevelSource.class.getClassLoader().getResourceAsStream(resourcePath);
        if (input == null) {
            throw new IllegalArgumentException("Level resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append('\n');
            }
            return new TextLevelSource(text.toString(), width, height);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Level load() {
        List<String> rows = rows();
        if (rows.size() > height) {
            throw new IllegalArgumentException("Level has " + rows.size() + " rows, map height is " + height);
        }
        GridPoint2 playerStart = null;
        List<Tree> trees = new ArrayList<Tree>();
        for (int row = 0; row < rows.size(); row++) {
            String line = rows.get(row);
            if (line.length() != width) {
                throw new IllegalArgumentException("Row " + row + " has length " + line.length() + ", expected " + width);
            }
            int y = height - 1 - row;
            for (int x = 0; x < line.length(); x++) {
                char cell = line.charAt(x);
                if (cell == EMPTY) {
                    continue;
                }
                if (cell == TREE) {
                    trees.add(new Tree(new GridPoint2(x, y)));
                } else if (cell == PLAYER) {
                    if (playerStart != null) {
                        throw new IllegalArgumentException("Level has more than one player start");
                    }
                    playerStart = new GridPoint2(x, y);
                } else {
                    throw new IllegalArgumentException("Unknown cell '" + cell + "' at " + x + "," + y);
                }
            }
        }
        if (playerStart == null) {
            throw new IllegalArgumentException("Level has no player start");
        }
        return new Level(playerStart, trees);
    }

    private List<String> rows() {
        String[] rawRows = text.split("\\R", -1);
        List<String> rows = new ArrayList<String>();
        for (String row : rawRows) {
            if (!row.isEmpty()) {
                rows.add(row);
            }
        }
        return rows;
    }
}
