package quinzical;

/**
 * This is the entry point for the application, as the entry point cannot extend Application.
 * This is to avoid the error of javaFX components missing on running the app.
 */
public class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}
