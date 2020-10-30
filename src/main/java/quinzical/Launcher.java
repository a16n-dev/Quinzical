package quinzical;

/**
 * This is the entry point for the application, as the entry point cannot extend
 * Application. This is to avoid the error of javaFX components missing on
 * running the app. This is a quirk of using a gradle setup. Reference:
 * https://stackoverflow.com/questions/52569724/javafx-11-create-a-jar-file-with-gradle
 */
public class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}
