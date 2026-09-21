package ru.mipt.bit.platformer.input;

import java.util.Arrays;
import java.util.List;

public class CompositeInputHandler implements InputHandler {

    private final List<InputHandler> handlers;

    public CompositeInputHandler(InputHandler... handlers) {
        this.handlers = Arrays.asList(handlers);
    }

    @Override
    public void handle() {
        for (InputHandler handler : handlers) {
            handler.handle();
        }
    }
}
