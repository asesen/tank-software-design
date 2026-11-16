package ru.mipt.bit.platformer.model;

public final class HeavyTankState implements TankState {

    public static final HeavyTankState INSTANCE = new HeavyTankState();

    private HeavyTankState() {
    }

    @Override
    public float movementDurationFactor() {
        return 3f;
    }

    @Override
    public boolean canShoot() {
        return false;
    }

    @Override
    public float shootCooldownFactor() {
        return 1f;
    }
}
