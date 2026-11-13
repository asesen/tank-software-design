package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.LevelListener;
import ru.mipt.bit.platformer.model.LevelObject;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameRenderer implements LevelListener, Disposable {

    private final Batch batch;
    private final LevelGraphics level;
    private final List<RenderableFactory> factories;
    private final Map<LevelObject, Renderable> views = new IdentityHashMap<LevelObject, Renderable>();
    private final List<LevelObject> drawOrder = new ArrayList<LevelObject>();

    public GameRenderer(Batch batch, LevelGraphics level, List<RenderableFactory> factories) {
        this.batch = batch;
        this.level = level;
        this.factories = new ArrayList<RenderableFactory>(factories);
    }

    static Renderable createView(LevelObject object, List<RenderableFactory> factories) {
        for (RenderableFactory factory : factories) {
            Renderable view = factory.create(object);
            if (view != null) {
                return view;
            }
        }
        throw new IllegalArgumentException("No graphics for " + object.getClass().getSimpleName());
    }

    @Override
    public void objectAdded(LevelObject object) {
        Renderable view = createView(object, factories);
        views.put(object, view);
        drawOrder.add(object);
    }

    @Override
    public void objectRemoved(LevelObject object) {
        Renderable view = views.remove(object);
        drawOrder.remove(object);
        if (view != null) {
            view.dispose();
        }
    }

    public void render() {
        for (LevelObject object : drawOrder) {
            views.get(object).sync();
        }
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        level.render();
        batch.begin();
        for (LevelObject object : drawOrder) {
            views.get(object).draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (Renderable view : views.values()) {
            view.dispose();
        }
        views.clear();
        drawOrder.clear();
        level.dispose();
        batch.dispose();
    }
}
