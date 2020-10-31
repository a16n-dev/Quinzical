package quinzical.util;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import quinzical.model.User;

/**
 * Helper class used as a wrapper around Socket IO. This is especially useful in
 * combination with JavaFX, because JavaFX requires callbacks to be executed in
 * a specific format and socket IO requires them in a different format. This
 * wrapper class allows you to provide callbacks which will work with JavaFX and
 * socket IO
 */
public class Connect {
    private static Connect instance;

    /**
     * Private constructor
     */
    private Connect() { }

    /**
     * Get the singleton instance of the class
     * @return instance
     */
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
            instance.makeConnection();
        }
        return instance;
    }

    private Socket socket;

    /**
     * Method to force a reconnection of the socket, regardless of whether it is or
     * is not connected.
     */
    public void forceReconnect() {
        if (socket != null) {
            socket.disconnect();
        }
        makeConnection();
    }

    /**
     * Establish a connection to the server with the logged in user.
     */
    private void makeConnection() {
        try {
            socket = IO.socket("http://13.210.217.144:3000/?token=" + User.getInstance().getToken());
            socket.connect();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Add a listener to the socket for a specific message type. Adding a listener
     * will remove any existing listeners on that message type. Compatible with
     * JavaFX GUI manipulations.
     * 
     * @param type    the type of message to listen for
     * @param message the callback to run when the message triggers
     */
    public void onMessage(String type, Message message) {
        socket.off(type);

        // socket IO uses Emitter.Listener and JavaFX requires runnables. However,
        // runnables take no parameters, meaning that - to pass the event data to the
        // callback - we must run a custom callback which takes the right signature of
        // argument
        socket.on(type, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        message.run(args);
                    }
                });
            }
        });
    }

    /**
     * Emit a message of the given type.
     * 
     * @param type the type of message
     * @param data the data to be included in the message. Should be valid JSON
     */
    public void emit(String type, Object data) {
        socket.emit(type, data);
    }
}