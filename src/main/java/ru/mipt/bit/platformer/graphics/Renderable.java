package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public interface Renderable extends Disposable {

    void draw(Batch batch);

    default void sync() {
    }
}
