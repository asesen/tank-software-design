package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeGraphics implements Disposable {

    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public TreeGraphics(Tree tree, String texturePath, TiledMapTileLayer groundLayer) {
        this.texture = new Texture(texturePath);
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getCell());
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, 0f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
