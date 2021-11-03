package com.asgla.util;

import com.asgla.avatar.Avatar;
import net.sf.json.JSONObject;

public class ServerMessage {

    public static final String WARNING = "Warning";
    public static final String GAME = "Game";

    public static JSONObject json(Avatar avatar, String message, Object... args) {
        return ServerMessage.json(avatar, String.format(message, args));
    }

    public static JSONObject json(Avatar avatar, String message) {
        return ServerMessage.json(avatar, 1, message);
    }

    public static JSONObject json(Avatar avatar, int channel, String message) {
        return new JSONObject()
            .element("cmd", RequestCommand.Chat)
            .element("channel", channel)
            .element("entity", new JSONObject()
                .element("entityId", avatar.id())
                .element("entityType", avatar.type().ordinal())
            )
            .element("entityTag", "Player")
            .element("message", message);
    }

    public static JSONObject json(String tag, String message, Object... args) {
        return ServerMessage.json(tag, String.format(message, args));
    }

    public static JSONObject json(String tag, String message) {
        return ServerMessage.json(tag, 1, message);
    }

    public static JSONObject json(String tag, int channel, String message) {
        return new JSONObject()
            .element("cmd", RequestCommand.Chat)
            .element("channel", channel)
            .element("entityTag", tag)
            .element("message", message);
    }

}
