package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

public class TreeRenderableFactory implements RenderableFactory {

    private final String texturePath;
    private final TileMovement tileMovement;

    public TreeRenderableFactory(String texturePath, TileMovement tileMovement) {
        this.texturePath = texturePath;
        this.tileMovement = tileMovement;
    }

    @Override
    public Renderable create(LevelObject object) {
        if (!(object instanceof Tree)) {
            return null;
        }
        return new TreeGraphics((Tree) object, texturePath, tileMovement);
    }
}
