package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TreeGraphics implements Renderable {

    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public TreeGraphics(Tree tree, String texturePath, TileMovement tileMovement) {
        this.texture = new Texture(texturePath);
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        GridPoint2 cell = tree.getCell();
        tileMovement.moveRectangleBetweenTileCenters(rectangle, cell, cell, 1f);
    }

    @Override
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, 0f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
