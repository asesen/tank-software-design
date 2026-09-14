package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final DirectionBinding[] MOVEMENT_BINDINGS = {
            new DirectionBinding(Direction.UP, UP, W),
            new DirectionBinding(Direction.LEFT, LEFT, A),
            new DirectionBinding(Direction.DOWN, DOWN, S),
            new DirectionBinding(Direction.RIGHT, RIGHT, D)
    };

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;
    private Tank tank;

    private Texture greenTreeTexture;
    private TextureRegion treeObstacleGraphics;
    private Rectangle treeObstacleRectangle;
    private Tree tree;
    private Field field;

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        playerGraphics = new TextureRegion(blueTankTexture);
        playerRectangle = createBoundingRectangle(playerGraphics);
        tank = new Tank(new GridPoint2(1, 1));

        greenTreeTexture = new Texture("images/greenTree.png");
        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        tree = new Tree(new GridPoint2(1, 3));
        field = new Field(tree);
        moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, tree.getCell());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        for (DirectionBinding binding : MOVEMENT_BINDINGS) {
            if (binding.isPressed()) {
                tank.tryMove(binding.direction, field);
            }
        }

        tileMovement.moveRectangleBetweenTileCenters(
                playerRectangle, tank.getCell(), tank.getDestination(), tank.getMovementProgress());
        tank.update(deltaTime);

        levelRenderer.render();

        batch.begin();
        drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, tank.getRotation());
        drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle, 0f);

        // submit all drawing requests
        batch.end();
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    private static final class DirectionBinding {
        private final Direction direction;
        private final int[] keys;

        private DirectionBinding(Direction direction, int... keys) {
            this.direction = direction;
            this.keys = keys;
        }

        private boolean isPressed() {
            for (int key : keys) {
                if (Gdx.input.isKeyPressed(key)) {
                    return true;
                }
            }
            return false;
        }
    }
}
