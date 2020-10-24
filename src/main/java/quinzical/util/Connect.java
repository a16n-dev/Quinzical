package quinzical.util;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import quinzical.controller.View;
import quinzical.model.User;

public class Connect {
    private static Connect instance;

    private Connect() { }

    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
            instance.makeConnection();
        }
        return instance;
    }

    private Socket socket;

    public void forceReconnect() {
        if (socket != null) {
            socket.disconnect();
        }
        makeConnection();
    }

    private void makeConnection() {
        try {
            socket = IO.socket("http://13.210.217.144:3000/?token=" + User.getInstance().getToken());
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

				@Override
				public void call(Object... args) {
				}
                
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

				@Override
				public void call(Object... args) {
				}

            });
            socket.connect();
        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void onMessage(String type, Message message) {
        socket.off(type);
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
    public void emit(String type, Object data) {
        socket.emit(type, data);
    }
}