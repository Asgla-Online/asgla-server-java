package com.asgla.util;

import net.sf.json.JSONObject;

public class ServerMessage {

    public static final String STAFF = "staff";
    public static final String GAME = "game";
    public static final String COMBAT = "combat";

    public static JSONObject json(String username, String message, Object... args) {
        return ServerMessage.json(username, String.format(message, args));
    }

    public static JSONObject json(String username, String message) {
        return ServerMessage.json(username, 1, message);
    }

    public static JSONObject json(String username, int channel, String message) {
        return new JSONObject()
            .element("cmd", RequestCommand.Chat)
            .element("channel", channel)
            .element("username", username)
            .element("message", message);
    }

}
