package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.control.ShootCommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShootingTest {

    @Test
    public void shootSpawnsBulletInFrontOfTank() {
        LogicalLevel level = new LogicalLevel(10, 8);
        Tank tank = new Tank(new GridPoint2(1, 1));
        level.add(tank);

        new ShootCommand(tank).execute();

        Bullet bullet = onlyBullet(level);
        assertEquals(new GridPoint2(2, 1), bullet.getCell());
        assertEquals(0f, bullet.getRotation(), 0f);
        assertEquals(Tank.BULLET_DAMAGE, bullet.getDamage());
    }

    @Test
    public void adjacentEnemyIsHitImmediately() {
        LogicalLevel level = new LogicalLevel(10, 8);
        Tank shooter = new Tank(new GridPoint2(1, 1));
        Tank enemy = new Tank(new GridPoint2(2, 1));
        level.add(shooter);
        level.add(enemy);

        shooter.shoot();
        level.tick(0f);

        assertFalse(enemy.isAlive());
        assertTrue(shooter.isAlive());
        assertEquals(0, bullets(level).size());
        assertFalse(level.getObjects().contains(enemy));
    }

    @Test
    public void bulletDisappearsOnTreeWithoutRemovingTree() {
        LogicalLevel level = new LogicalLevel(10, 8);
        Tank tank = new Tank(new GridPoint2(1, 1));
        Tree tree = new Tree(new GridPoint2(2, 1));
        level.add(tank);
        level.add(tree);

        tank.shoot();
        level.tick(0f);

        assertTrue(level.getObjects().contains(tree));
        assertEquals(0, bullets(level).size());
    }

    @Test
    public void bulletFliesForwardAndLeavesTheMap() {
        LogicalLevel level = new LogicalLevel(3, 3);
        Tank tank = new Tank(new GridPoint2(0, 1));
        level.add(tank);

        tank.shoot();
        level.tick(0f);
        assertEquals(new GridPoint2(1, 1), onlyBullet(level).getCell());

        level.tick(Tank.BULLET_STEP_SECONDS);
        assertEquals(new GridPoint2(2, 1), onlyBullet(level).getCell());

        level.tick(Tank.BULLET_STEP_SECONDS);
        assertEquals(0, bullets(level).size());
    }

    @Test
    public void bulletAimedOffTheMapDisappears() {
        LogicalLevel level = new LogicalLevel(10, 8);
        Tank tank = new Tank(new GridPoint2(0, 0));
        level.add(tank);
        tank.tryMove(Direction.LEFT, level);

        tank.shoot();
        level.tick(0f);

        assertEquals(0, bullets(level).size());
        assertTrue(tank.isAlive());
    }

    @Test
    public void twoBulletsRemoveEachOther() {
        LogicalLevel level = new LogicalLevel(10, 8);
        Tank left = new Tank(new GridPoint2(0, 1));
        Tank right = new Tank(new GridPoint2(3, 1));
        level.add(left);
        level.add(right);
        right.tryMove(Direction.LEFT, level);

        left.shoot();
        right.shoot();
        level.tick(0f);

        assertEquals(0, bullets(level).size());
        assertTrue(left.isAlive());
        assertTrue(right.isAlive());
    }

    @Test
    public void secondShotWaitsForCooldown() {
        LogicalLevel level = new LogicalLevel(10, 8);
        Tank tank = new Tank(new GridPoint2(1, 1));
        level.add(tank);

        tank.shoot();
        tank.shoot();

        assertEquals(1, bullets(level).size());
    }

    @Test
    public void listenerSeesObjectsAddedAndRemoved() {
        LogicalLevel level = new LogicalLevel(10, 8);
        List<String> events = new ArrayList<String>();
        level.addListener(new LevelListener() {
            @Override
            public void objectAdded(LevelObject object) {
                events.add("add " + object.getClass().getSimpleName());
            }

            @Override
            public void objectRemoved(LevelObject object) {
                events.add("remove " + object.getClass().getSimpleName());
            }
        });
        Tank tank = new Tank(new GridPoint2(0, 0));
        level.add(tank);
        tank.tryMove(Direction.LEFT, level);
        tank.shoot();
        level.tick(0f);
        tank.takeDamage(Tank.BULLET_DAMAGE);

        assertEquals("add Tank", events.get(0));
        assertEquals("add Bullet", events.get(1));
        assertTrue(events.contains("remove Bullet"));
        assertEquals("remove Tank", events.get(events.size() - 1));
    }

    private static Bullet onlyBullet(LogicalLevel level) {
        List<Bullet> bullets = bullets(level);
        assertEquals(1, bullets.size());
        return bullets.get(0);
    }

    private static List<Bullet> bullets(LogicalLevel level) {
        List<Bullet> bullets = new ArrayList<Bullet>();
        for (LevelObject object : level.getObjects()) {
            if (object instanceof Bullet) {
                bullets.add((Bullet) object);
            }
        }
        return bullets;
    }
}
