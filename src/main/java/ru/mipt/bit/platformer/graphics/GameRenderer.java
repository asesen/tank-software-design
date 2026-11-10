package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.LevelListener;
import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameRenderer implements LevelListener, Disposable {

    private final Batch batch;
    private final LevelGraphics level;
    private final String tankTexture;
    private final String treeTexture;
    private final HealthDisplay healthDisplay;
    private final Map<LevelObject, Renderable> views = new IdentityHashMap<LevelObject, Renderable>();
    private final List<LevelObject> drawOrder = new ArrayList<LevelObject>();

    public GameRenderer(Batch batch, LevelGraphics level, String tankTexture, String treeTexture, HealthDisplay healthDisplay) {
        this.batch = batch;
        this.level = level;
        this.tankTexture = tankTexture;
        this.treeTexture = treeTexture;
        this.healthDisplay = healthDisplay;
    }

    @Override
    public void objectAdded(LevelObject object) {
        Renderable view = createView(object);
        if (view == null) {
            return;
        }
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

    private Renderable createView(LevelObject object) {
        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            return new HealthBarDecorator(
                    new TankGraphics(tank, tankTexture, level.getTileMovement()),
                    tank,
                    level.getTileMovement(),
                    healthDisplay
            );
        }
        if (object instanceof Tree) {
            return new TreeGraphics((Tree) object, treeTexture, level.getTileMovement());
        }
        if (object instanceof Bullet) {
            return new BulletGraphics((Bullet) object, level.getTileMovement());
        }
        return null;
    }
}
