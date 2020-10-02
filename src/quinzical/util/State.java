package quinzical.util;

/**
 * An enum to keep track of all the different state objects that should be persisted
 */
public enum State {
    GAME("game"),
    USER("user"),
    TTS("tts"),
    ;
    private final String fileName;

    private State(String name){
        this.fileName = name;
    }

    public String getFileName() {
        return this.fileName;
    }
}
