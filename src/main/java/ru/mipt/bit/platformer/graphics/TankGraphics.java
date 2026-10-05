package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TankGraphics implements Renderable {

    private final Tank tank;
    private final TileMovement tileMovement;
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public TankGraphics(Tank tank, String texturePath, TileMovement tileMovement) {
        this.tank = tank;
        this.tileMovement = tileMovement;
        this.texture = new Texture(texturePath);
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
    }

    @Override
    public void sync() {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle, tank.getCell(), tank.getDestination(), tank.getMovementProgress());
    }

    @Override
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, tank.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
