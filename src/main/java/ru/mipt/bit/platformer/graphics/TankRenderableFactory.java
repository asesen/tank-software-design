package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankRenderableFactory implements RenderableFactory {

    private final String texturePath;
    private final TileMovement tileMovement;
    private final HealthDisplay healthDisplay;

    public TankRenderableFactory(String texturePath, TileMovement tileMovement, HealthDisplay healthDisplay) {
        this.texturePath = texturePath;
        this.tileMovement = tileMovement;
        this.healthDisplay = healthDisplay;
    }

    @Override
    public Renderable create(LevelObject object) {
        if (!(object instanceof Tank)) {
            return null;
        }
        Tank tank = (Tank) object;
        return new HealthBarDecorator(
                new TankGraphics(tank, texturePath, tileMovement),
                tank,
                tileMovement,
                healthDisplay
        );
    }
}
