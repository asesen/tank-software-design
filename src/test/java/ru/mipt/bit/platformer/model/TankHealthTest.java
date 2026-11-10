package ru.mipt.bit.platformer.model;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TankHealthTest {

    @Test
    public void rolledHealthStaysBetween80And100() {
        Random random = new Random(1);
        for (int i = 0; i < 30; i++) {
            int health = Tank.rollHealth(random);
            assertTrue(health >= 80 && health <= 100);
        }
    }
}
