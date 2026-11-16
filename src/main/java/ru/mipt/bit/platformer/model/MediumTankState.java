package ru.mipt.bit.platformer.model;

public final class MediumTankState implements TankState {

    public static final MediumTankState INSTANCE = new MediumTankState();

    private MediumTankState() {
    }

    @Override
    public float movementDurationFactor() {
        return 2f;
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
