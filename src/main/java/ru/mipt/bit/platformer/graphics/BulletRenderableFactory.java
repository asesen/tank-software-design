package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.util.TileMovement;

public class BulletRenderableFactory implements RenderableFactory {

    private final TileMovement tileMovement;

    public BulletRenderableFactory(TileMovement tileMovement) {
        this.tileMovement = tileMovement;
    }

    @Override
    public Renderable create(LevelObject object) {
        if (!(object instanceof Bullet)) {
            return null;
        }
        return new BulletGraphics((Bullet) object, tileMovement);
    }
}
