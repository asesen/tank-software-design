package ru.mipt.bit.platformer.model;

public final class LightTankState implements TankState {

    public static final LightTankState INSTANCE = new LightTankState();

    private LightTankState() {
    }

    @Override
    public float movementDurationFactor() {
        return 1f;
    }

    @Override
    public boolean canShoot() {
        return true;
    }

    @Override
    public float shootCooldownFactor() {
        return 1f;
    }
}
