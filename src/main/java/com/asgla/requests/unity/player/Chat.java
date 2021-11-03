package com.asgla.requests.unity.player;

import com.asgla.avatar.player.Player;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.util.ServerMessage;
import net.sf.json.JSONObject;

import static com.asgla.Main.serverController;

@DatabaseOpen
public class Chat implements IRequest {
    @Override
    public void onRequest(Player player, RequestArgs args) {
        if (args.length != 2) {
            return;
        }

        Integer channel = args.getInt(0);
        String message = args.getStr(1);

        JSONObject chat = ServerMessage.json(player, channel, message);

        switch (channel) {
            case 0 -> //All
                serverController().dispatch(chat);
            case 1 -> //Global
                serverController().dispatch(chat);
            case 2 -> //Zone
                player.gameRoom().dispatch(chat);
            case 3 -> //Trade
                serverController().dispatch(chat);
            case 4 -> //Party
                serverController().dispatch(chat);
            case 5 -> //Guild
                serverController().dispatch(chat);
            default -> {
                //Unknown zone
            }
        }
    }
}
