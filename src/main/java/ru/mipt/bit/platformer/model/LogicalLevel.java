package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogicalLevel implements Occupancy {

    private final LevelBounds bounds;
    private final List<LevelObject> objects = new ArrayList<LevelObject>();
    private final List<LevelListener> listeners = new ArrayList<LevelListener>();

    public LogicalLevel(int width, int height) {
        this.bounds = new LevelBounds(width, height);
    }

    public void addListener(LevelListener listener) {
        listeners.add(listener);
    }

    public void add(LevelObject object) {
        objects.add(object);
        object.onAdded(this);
        for (LevelListener listener : listeners) {
            listener.objectAdded(object);
        }
    }

    public void remove(LevelObject object) {
        if (!objects.remove(object)) {
            return;
        }
        for (LevelListener listener : listeners) {
            listener.objectRemoved(object);
        }
    }

    public void tick(float deltaTime) {
        List<LevelObject> snapshot = new ArrayList<LevelObject>(objects);
        for (LevelObject object : snapshot) {
            if (objects.contains(object)) {
                object.live(deltaTime);
            }
        }
    }

    public boolean isOutside(GridPoint2 cell) {
        return bounds.isOccupied(cell);
    }

    public LevelObject findOther(GridPoint2 cell, LevelObject self, LevelObject ignore) {
        for (LevelObject object : objects) {
            if (object == self || object == ignore) {
                continue;
            }
            if (object.occupies(cell)) {
                return object;
            }
        }
        return null;
    }

    public List<LevelObject> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    @Override
    public boolean isOccupied(GridPoint2 cell) {
        if (bounds.isOccupied(cell)) {
            return true;
        }
        for (LevelObject object : objects) {
            if (object.occupies(cell)) {
                return true;
            }
        }
        return false;
    }
}
