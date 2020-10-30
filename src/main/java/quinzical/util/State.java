package quinzical.util;

/**
 * An enum to keep track of all the different state objects that should be
 * persisted to file
 * 
 * @author Alexander Nicholson, Peter Geodeke
 */
public enum State {
    GAME("game"), USER("user"), TTS("tts"), MUSIC("music");

    private final String fileName;

    private State(String name) {
        this.fileName = name;
    }

    /**
     * @return the file the state should be written to and read from
     */
    public String getFileName() {
        return this.fileName;
    }
}
