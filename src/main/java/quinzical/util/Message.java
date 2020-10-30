package quinzical.util;

/**
 * Functional Interface used in the Connect class to interface between socket IO
 * and JavaFX
 */
@FunctionalInterface
public interface Message {
    void run(Object... args);
}
