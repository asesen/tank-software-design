package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Occupancy;
import ru.mipt.bit.platformer.model.Tank;

public interface ControlStrategy {

    Command decide(Tank tank, Occupancy occupancy);
}
