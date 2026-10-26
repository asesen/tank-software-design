package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tank {

    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;

    private final GridPoint2 cell;
    private final GridPoint2 destination;
    private final float movementSpeed;
    private float movementProgress = 1f;
    private Direction direction = Direction.RIGHT;

    public Tank(GridPoint2 startCell) {
        this(startCell, DEFAULT_MOVEMENT_SPEED);
    }

    public Tank(GridPoint2 startCell, float movementSpeed) {
        this.cell = new GridPoint2(startCell);
        this.destination = new GridPoint2(startCell);
        this.movementSpeed = movementSpeed;
    }

    public void tryMove(Direction direction, Occupancy occupancy) {
        if (!hasReachedDestination()) {
            return;
        }
        GridPoint2 nextCell = direction.addDirection(cell);
        if (!occupancy.isOccupied(nextCell)) {
            destination.set(nextCell);
            movementProgress = 0f;
        }
        this.direction = direction;
    }

    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (hasReachedDestination()) {
            cell.set(destination);
        }
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
        return cell.equals(other) || destination.equals(other);
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
