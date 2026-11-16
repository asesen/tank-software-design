package ru.mipt.bit.platformer.model;

public interface TankState {

        float movementDurationFactor();

    boolean canShoot();

        float shootCooldownFactor();

        static TankState forHealth(int health, int maxHealth) {
        if (maxHealth <= 0) {
            return HeavyTankState.INSTANCE;
        }
        double ratio = health / (double) maxHealth;
        if (ratio > 0.70d) {
            return LightTankState.INSTANCE;
        }
        if (ratio >= 0.15d) {
            return MediumTankState.INSTANCE;
        }
        return HeavyTankState.INSTANCE;
    }
}
