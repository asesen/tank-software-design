package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tank implements LevelObject {

    public static final int DEFAULT_HEALTH = 1;
    public static final int BULLET_DAMAGE = 1;
    public static final float BULLET_STEP_SECONDS = 0.2f;
    public static final float SHOOT_COOLDOWN = 0.25f;

    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;

    private final GridPoint2 cell;
    private final GridPoint2 destination;
    private final float movementSpeed;
    private float movementProgress = 1f;
    private Direction direction = Direction.RIGHT;
    private int health = DEFAULT_HEALTH;
    private float shootCooldown;
    private boolean alive = true;
    private LogicalLevel level;

    public Tank(GridPoint2 startCell) {
        this(startCell, DEFAULT_MOVEMENT_SPEED);
    }

    public Tank(GridPoint2 startCell, float movementSpeed) {
        this.cell = new GridPoint2(startCell);
        this.destination = new GridPoint2(startCell);
        this.movementSpeed = movementSpeed;
    }

    public void tryMove(Direction direction, Occupancy occupancy) {
        if (!alive || !hasReachedDestination()) {
            return;
        }
        GridPoint2 nextCell = direction.addDirection(cell);
        if (!occupancy.isOccupied(nextCell)) {
            destination.set(nextCell);
            movementProgress = 0f;
        }
        this.direction = direction;
    }

    public void shoot() {
        if (!alive || level == null || shootCooldown > 0f) {
            return;
        }
        shootCooldown = SHOOT_COOLDOWN;
        Bullet bullet = new Bullet(
                direction.addDirection(cell),
                direction,
                BULLET_DAMAGE,
                BULLET_STEP_SECONDS,
                this
        );
        level.add(bullet);
    }

    public void takeDamage(int amount) {
        if (!alive) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            alive = false;
            if (level != null) {
                level.remove(this);
            }
        }
    }

    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (hasReachedDestination()) {
            cell.set(destination);
        }
    }

    @Override
    public void live(float tick) {
        if (!alive) {
            return;
        }
        if (shootCooldown > 0f) {
            shootCooldown -= tick;
        }
        update(tick);
    }

    @Override
    public void onAdded(LogicalLevel level) {
        this.level = level;
    }

    public boolean hasReachedDestination() {
        return movementProgress >= 1f;
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

    public Direction getDirection() {
        return direction;
    }

    public boolean occupies(GridPoint2 other) {
        return alive && (cell.equals(other) || destination.equals(other));
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    private static float continueProgress(float previousProgress, float deltaTime, float speed) {
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
