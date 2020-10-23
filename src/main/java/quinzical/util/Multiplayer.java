package quinzical.util;

import org.json.JSONException;
import org.json.JSONObject;

import quinzical.controller.View;

public class Multiplayer {
    public static void joinLobby(String code) {
        Connect connect = Connect.getInstance();
        
        JSONObject json = new JSONObject();
        try {
            json.put("code", code);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        connect.emit("JOIN_LOBBY", json);
        connect.onMessage("LOBBY_JOINED", args -> {
            Router.show(View.LOBBY);
        });
        connect.onMessage("INVALID_LOBBY", args -> {
            System.out.println(": (");
        });
    }
}
