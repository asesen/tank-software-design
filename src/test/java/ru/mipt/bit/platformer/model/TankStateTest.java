package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TankStateTest {

    private final Occupancy free = new Occupancy() {
        @Override
        public boolean isOccupied(GridPoint2 cell) {
            return false;
        }
    };

    @Test
    public void healthThresholdsPickLightMediumAndHeavy() {
        assertSame(LightTankState.INSTANCE, TankState.forHealth(71, 100));
        assertSame(MediumTankState.INSTANCE, TankState.forHealth(70, 100));
        assertSame(MediumTankState.INSTANCE, TankState.forHealth(15, 100));
        assertSame(HeavyTankState.INSTANCE, TankState.forHealth(14, 100));
    }

    @Test
    public void lightTankCrossesACellAtBaseSpeed() {
        Tank tank = damaged(29);
        assertSame(LightTankState.INSTANCE, tank.getState());

        tank.tryMove(Direction.RIGHT, free);
        tank.update(0.4f);

        assertTrue(tank.hasReachedDestination());
    }

    @Test
    public void mediumTankTakesTwiceAsLong() {
        Tank tank = damaged(30);
        assertSame(MediumTankState.INSTANCE, tank.getState());

        tank.tryMove(Direction.RIGHT, free);
        tank.update(0.4f);
        assertFalse(tank.hasReachedDestination());
        tank.update(0.4f);

        assertTrue(tank.hasReachedDestination());
    }

    @Test
    public void mediumTankCanStillShoot() {
        LogicalLevel level = new LogicalLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, 100);
        level.add(tank);
        tank.takeDamage(30);

        tank.shoot();

        assertEquals(1, countBullets(level));
    }

    @Test
    public void heavyTankIsSlowerAndCannotShoot() {
        LogicalLevel level = new LogicalLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, 100);
        level.add(tank);
        tank.takeDamage(86);
        assertSame(HeavyTankState.INSTANCE, tank.getState());

        tank.shoot();
        assertEquals(0, countBullets(level));

        tank.tryMove(Direction.RIGHT, free);
        tank.update(0.8f);
        assertFalse(tank.hasReachedDestination());
        tank.update(0.4f);
        assertTrue(tank.hasReachedDestination());
    }

    @Test
    public void externalStateStaysUntilNextDamage() {
        Tank tank = new Tank(new GridPoint2(0, 0), 0.4f, 100);
        TankState boosted = new TankState() {
            @Override
            public float movementDurationFactor() {
                return 0.5f;
            }

            @Override
            public boolean canShoot() {
                return true;
            }

            @Override
            public float shootCooldownFactor() {
                return 0.5f;
            }
        };
        tank.setState(boosted);

        tank.tryMove(Direction.RIGHT, free);
        tank.update(0.2f);
        assertTrue(tank.hasReachedDestination());

        tank.takeDamage(1);
        assertSame(LightTankState.INSTANCE, tank.getState());
    }

    private static Tank damaged(int amount) {
        Tank tank = new Tank(new GridPoint2(0, 0), 0.4f, 100);
        tank.takeDamage(amount);
        return tank;
    }

    private static int countBullets(LogicalLevel level) {
        int count = 0;
        for (LevelObject object : level.getObjects()) {
            if (object instanceof Bullet) {
                count++;
            }
        }
        return count;
    }
}
