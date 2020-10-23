package quinzical.util;

@FunctionalInterface
public interface Message {
    void run(Object... args);
}
