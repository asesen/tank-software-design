package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameRenderer implements Disposable {

    private final Batch batch;
    private final LevelGraphics level;
    private final List<Renderable> renderables;

    public GameRenderer(Batch batch, LevelGraphics level, List<Renderable> renderables) {
        this.batch = batch;
        this.level = level;
        this.renderables = new ArrayList<Renderable>(renderables);
    }

    public void sync() {
        for (Renderable renderable : renderables) {
            renderable.sync();
        }
    }

    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        level.render();
        batch.begin();
        for (Renderable renderable : renderables) {
            renderable.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (Renderable renderable : renderables) {
            renderable.dispose();
        }
        level.dispose();
        batch.dispose();
    }
}
