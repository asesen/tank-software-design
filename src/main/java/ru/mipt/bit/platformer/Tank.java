package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank {

    private static final float MOVEMENT_SPEED = 0.4f;

    private final GridPoint2 cell;
    private final GridPoint2 destination;
    private float movementProgress = 1f;
    private Direction direction = Direction.RIGHT;

    public Tank(GridPoint2 startCell) {
        this.cell = new GridPoint2(startCell);
        this.destination = new GridPoint2(startCell);
    }

    public void tryMove(Direction direction, Field field) {
        if (!hasReachedDestination()) {
            return;
        }
        GridPoint2 nextCell = direction.addDirection(cell);
        if (!field.isOccupied(nextCell)) {
            destination.set(nextCell);
            movementProgress = 0f;
        }
        this.direction = direction;
    }

    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (hasReachedDestination()) {
            cell.set(destination);
        }
    }

    private boolean hasReachedDestination() {
        return isEqual(movementProgress, 1f);
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
}
