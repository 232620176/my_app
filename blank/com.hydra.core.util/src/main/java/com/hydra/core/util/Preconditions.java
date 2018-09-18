package com.hydra.core.util;

public final class Preconditions {
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    private Preconditions() {
        throw new UnsupportedOperationException();
    }
}
