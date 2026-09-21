package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.GameRenderer;
import ru.mipt.bit.platformer.input.CompositeInputHandler;
import ru.mipt.bit.platformer.input.GdxKeyboard;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.MovementInputHandler;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

public class GameDesktopLauncher implements ApplicationListener {

    private Tank tank;
    private InputHandler inputHandler;
    private GameRenderer renderer;

    @Override
    public void create() {
        tank = new Tank(new GridPoint2(1, 1));
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(tree);
        inputHandler = new CompositeInputHandler(
                new MovementInputHandler(tank, field, new GdxKeyboard())
        );
        renderer = new GameRenderer(tank, field);
    }

    @Override
    public void render() {
        inputHandler.handle();
        renderer.updateTankPosition();
        tank.update(Gdx.graphics.getDeltaTime());
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
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
