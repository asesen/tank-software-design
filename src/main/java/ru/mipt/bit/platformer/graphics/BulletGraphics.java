package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class BulletGraphics implements Renderable {

    private final Bullet bullet;
    private final TileMovement tileMovement;
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public BulletGraphics(Bullet bullet, TileMovement tileMovement) {
        this.bullet = bullet;
        this.tileMovement = tileMovement;
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fill();
        this.texture = new Texture(pixmap);
        pixmap.dispose();
        this.region = new TextureRegion(texture);
        this.rectangle = new Rectangle(0f, 0f, region.getRegionWidth(), region.getRegionHeight());
    }

    @Override
    public void sync() {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle, bullet.getCell(), bullet.getDestination(), bullet.getMovementProgress());
    }

    @Override
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, bullet.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
