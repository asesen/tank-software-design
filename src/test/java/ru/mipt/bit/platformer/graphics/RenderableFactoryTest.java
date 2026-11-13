package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.junit.Test;
import ru.mipt.bit.platformer.model.LevelObject;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertSame;

public class RenderableFactoryTest {

    @Test
    public void createViewUsesTheFactoryThatAcceptsTheObject() {
        LevelObject object = new LevelObject() {
            @Override
            public void live(float tick) {
            }

            @Override
            public boolean occupies(com.badlogic.gdx.math.GridPoint2 cell) {
                return false;
            }
        };
        Renderable expected = new Renderable() {
            @Override
            public void draw(Batch batch) {
            }

            @Override
            public void dispose() {
            }
        };
        RenderableFactory skip = new RenderableFactory() {
            @Override
            public Renderable create(LevelObject candidate) {
                return null;
            }
        };
        RenderableFactory match = new RenderableFactory() {
            @Override
            public Renderable create(LevelObject candidate) {
                return candidate == object ? expected : null;
            }
        };

        assertSame(expected, GameRenderer.createView(object, Arrays.asList(skip, match)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createViewFailsWhenNoFactoryAcceptsTheObject() {
        LevelObject object = new LevelObject() {
            @Override
            public void live(float tick) {
            }

            @Override
            public boolean occupies(com.badlogic.gdx.math.GridPoint2 cell) {
                return false;
            }
        };
        GameRenderer.createView(object, Collections.<RenderableFactory>emptyList());
    }
}
