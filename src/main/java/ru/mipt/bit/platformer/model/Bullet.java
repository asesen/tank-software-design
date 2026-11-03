package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Bullet implements LevelObject {

    private final GridPoint2 cell;
    private final GridPoint2 destination;
    private final Direction direction;
    private final int damage;
    private final float stepSeconds;
    private final Tank shooter;
    private LogicalLevel level;
    private float movementProgress = 1f;
    private boolean spawnChecked;
    private boolean active = true;

    public Bullet(GridPoint2 startCell, Direction direction, int damage, float stepSeconds, Tank shooter) {
        this.cell = new GridPoint2(startCell);
        this.destination = new GridPoint2(startCell);
        this.direction = direction;
        this.damage = damage;
        this.stepSeconds = stepSeconds;
        this.shooter = shooter;
    }

    @Override
    public void onAdded(LogicalLevel level) {
        this.level = level;
    }

    @Override
    public void live(float tick) {
        if (!active || level == null) {
            return;
        }
        if (!spawnChecked) {
            spawnChecked = true;
            if (resolve(cell)) {
                return;
            }
            aimNextCell();
            movementProgress = 0f;
        }
        movementProgress = advance(movementProgress, tick, stepSeconds);
        if (movementProgress >= 1f) {
            cell.set(destination);
            if (resolve(cell)) {
                return;
            }
            aimNextCell();
            movementProgress = 0f;
        }
    }

    @Override
    public boolean occupies(GridPoint2 other) {
        return active && (cell.equals(other) || destination.equals(other));
    }

    public GridPoint2 getCell() {
        return new GridPoint2(cell);
    }

    public GridPoint2 getDestination() {
        return new GridPoint2(destination);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return direction.getRotation();
    }

    public int getDamage() {
        return damage;
    }

    private void aimNextCell() {
        destination.set(direction.addDirection(cell));
    }

    private boolean resolve(GridPoint2 point) {
        if (level.isOutside(point)) {
            vanish();
            return true;
        }
        LevelObject other = level.findOther(point, this, shooter);
        if (other == null) {
            return false;
        }
        if (other instanceof Tank) {
            ((Tank) other).takeDamage(damage);
        }
        vanish();
        if (other instanceof Bullet) {
            level.remove(other);
        }
        return true;
    }

    private void vanish() {
        active = false;
        level.remove(this);
    }

    private static float advance(float previousProgress, float deltaTime, float speed) {
        float next = previousProgress + deltaTime / speed;
        if (next < 0f) {
            return 0f;
        }
        if (next > 1f) {
            return 1f;
        }
        return next;
    }
}
