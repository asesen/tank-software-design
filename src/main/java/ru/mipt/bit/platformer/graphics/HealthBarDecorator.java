package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

public class HealthBarDecorator implements Renderable {

    private static final float BAR_WIDTH = 56f;
    private static final float BAR_HEIGHT = 8f;
    private static final float BAR_OFFSET_Y = 36f;

    private final Renderable delegate;
    private final Tank tank;
    private final TileMovement tileMovement;
    private final HealthDisplay display;
    private final Rectangle marker = new Rectangle(0f, 0f, 1f, 1f);
    private Texture pixel;

    public HealthBarDecorator(Renderable delegate, Tank tank, TileMovement tileMovement, HealthDisplay display) {
        this.delegate = delegate;
        this.tank = tank;
        this.tileMovement = tileMovement;
        this.display = display;
    }

    @Override
    public void sync() {
        delegate.sync();
    }

    @Override
    public void draw(Batch batch) {
        delegate.draw(batch);
        if (!display.isVisible() || tank.getMaxHealth() <= 0) {
            return;
        }
        tileMovement.moveRectangleBetweenTileCenters(
                marker, tank.getCell(), tank.getDestination(), tank.getMovementProgress());
        float x = marker.x + marker.width / 2f - BAR_WIDTH / 2f;
        float y = marker.y + marker.height / 2f + BAR_OFFSET_Y;
        float filled = BAR_WIDTH * tank.getHealth() / (float) tank.getMaxHealth();
        Texture texture = pixel();
        batch.setColor(0.15f, 0.15f, 0.15f, 1f);
        batch.draw(texture, x, y, BAR_WIDTH, BAR_HEIGHT);
        batch.setColor(0.2f, 0.75f, 0.25f, 1f);
        batch.draw(texture, x, y, Math.max(0f, filled), BAR_HEIGHT);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void dispose() {
        delegate.dispose();
        if (pixel != null) {
            pixel.dispose();
        }
    }

    private Texture pixel() {
        if (pixel == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 1f);
            pixmap.fill();
            pixel = new Texture(pixmap);
            pixmap.dispose();
        }
        return pixel;
    }
}
