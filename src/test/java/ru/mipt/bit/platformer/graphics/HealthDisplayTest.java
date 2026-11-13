package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.junit.Test;
import ru.mipt.bit.platformer.control.ToggleHealthDisplayCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HealthDisplayTest {

    @Test
    public void toggleCommandShowsThenHidesBars() {
        HealthDisplay display = new HealthDisplay();

        new ToggleHealthDisplayCommand(display).execute();
        assertTrue(display.isVisible());

        new ToggleHealthDisplayCommand(display).execute();
        assertFalse(display.isVisible());
    }

    @Test
    public void decoratorDrawsTankEvenWhenBarsAreHidden() {
        HealthDisplay display = new HealthDisplay();
        CountingRenderable tank = new CountingRenderable();
        HealthBarDecorator decorator = new HealthBarDecorator(tank, null, null, display);

        decorator.draw(null);

        assertEquals(1, tank.draws);
    }

    private static final class CountingRenderable implements Renderable {
        private int draws;

        @Override
        public void draw(Batch batch) {
            draws++;
        }

        @Override
        public void dispose() {
        }
    }
}
