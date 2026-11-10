package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.control.Command;
import ru.mipt.bit.platformer.control.KeyboardControlStrategy;
import ru.mipt.bit.platformer.control.RandomDirectionStrategy;
import ru.mipt.bit.platformer.control.TankDriver;
import ru.mipt.bit.platformer.graphics.GameRenderer;
import ru.mipt.bit.platformer.graphics.HealthDisplay;
import ru.mipt.bit.platformer.graphics.LevelGraphics;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.input.GdxKeyboard;
import ru.mipt.bit.platformer.input.KeyDirectionBinding;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.LevelSource;
import ru.mipt.bit.platformer.level.RandomLevelSource;
import ru.mipt.bit.platformer.level.RandomTankPlacement;
import ru.mipt.bit.platformer.level.TextLevelSource;
import ru.mipt.bit.platformer.model.AnyOccupancy;
import ru.mipt.bit.platformer.model.LogicalLevel;
import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.TankOccupancy;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SPACE;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    private static final int RANDOM_OBSTACLE_COUNT = 12;
    private static final int AI_TANK_COUNT = 3;
    private static final String LEVEL_MAP = "level.tmx";
    private static final String LEVEL_FILE = "levels/level.txt";
    private static final String TANK_TEXTURE = "images/tank_blue.png";
    private static final String TREE_TEXTURE = "images/greenTree.png";

    private final String levelSourceName;
    private List<TankDriver> drivers;
    private HealthDisplay healthDisplay;
    private LogicalLevel world;
    private GameRenderer renderer;

    public GameDesktopLauncher(String levelSourceName) {
        this.levelSourceName = levelSourceName;
    }

    @Override
    public void create() {
        Batch batch = new SpriteBatch();
        LevelGraphics levelGraphics = new LevelGraphics(LEVEL_MAP, batch);
        Level level = levelSource(levelGraphics.getWidth(), levelGraphics.getHeight()).load();
        List<Tank> tanks = createTanks(level);
        healthDisplay = new HealthDisplay();
        world = new LogicalLevel(level.getWidth(), level.getHeight());
        renderer = new GameRenderer(batch, levelGraphics, TANK_TEXTURE, TREE_TEXTURE, healthDisplay);
        world.addListener(renderer);
        for (Tree tree : level.getTrees()) {
            world.add(tree);
        }
        for (Tank tank : tanks) {
            world.add(tank);
        }
        drivers = createDrivers(tanks, world, healthDisplay);
    }

    private static List<Tank> createTanks(Level level) {
        List<Tank> tanks = new ArrayList<Tank>();
        Random random = new Random();
        Tank player = new Tank(level.getPlayerStart(), MOVEMENT_SPEED, Tank.rollHealth(random));
        tanks.add(player);
        Occupancy blocked = new AnyOccupancy(level.toField(), new TankOccupancy(tanks));
        List<GridPoint2> aiCells = new RandomTankPlacement(
                level.getWidth(), level.getHeight(), AI_TANK_COUNT, new Random()
        ).place(blocked);
        for (GridPoint2 cell : aiCells) {
            tanks.add(new Tank(cell, MOVEMENT_SPEED, Tank.rollHealth(random)));
        }
        return tanks;
    }

    private static List<TankDriver> createDrivers(List<Tank> tanks, Occupancy world, HealthDisplay healthDisplay) {
        List<TankDriver> drivers = new ArrayList<TankDriver>();
        drivers.add(new TankDriver(tanks.get(0), new KeyboardControlStrategy(new GdxKeyboard(), Arrays.asList(
                new KeyDirectionBinding(Direction.UP, UP, W),
                new KeyDirectionBinding(Direction.LEFT, LEFT, A),
                new KeyDirectionBinding(Direction.DOWN, DOWN, S),
                new KeyDirectionBinding(Direction.RIGHT, RIGHT, D)
        ), SPACE, L, healthDisplay), world));
        Random random = new Random();
        for (int i = 1; i < tanks.size(); i++) {
            drivers.add(new TankDriver(tanks.get(i), new RandomDirectionStrategy(random), world));
        }
        return drivers;
    }

    private LevelSource levelSource(int width, int height) {
        if ("file".equals(levelSourceName)) {
            return TextLevelSource.fromClasspath(LEVEL_FILE, width, height);
        }
        if ("random".equals(levelSourceName)) {
            return new RandomLevelSource(width, height, RANDOM_OBSTACLE_COUNT, new Random());
        }
        throw new IllegalArgumentException("Level source must be 'file' or 'random', got: " + levelSourceName);
    }

    @Override
    public void render() {
        List<Command> commands = new ArrayList<Command>();
        for (TankDriver driver : drivers) {
            commands.add(driver.plan());
        }
        for (Command command : commands) {
            command.execute();
        }
        world.tick(Gdx.graphics.getDeltaTime());
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(levelSourceName(args)), config);
    }

    private static String levelSourceName(String[] args) {
        if (args.length == 0) {
            return "file";
        }
        return args[0];
    }
}
