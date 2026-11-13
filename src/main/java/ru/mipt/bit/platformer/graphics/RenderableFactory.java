package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.LevelObject;

public interface RenderableFactory {

    Renderable create(LevelObject object);
}
