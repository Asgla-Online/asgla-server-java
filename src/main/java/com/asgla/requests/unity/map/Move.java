package com.asgla.requests.unity.map;

import com.asgla.avatar.player.Player;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.util.RequestCommand;
import com.asgla.util.Vector2;
import net.sf.json.JSONObject;

public class Move implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
        if (args.length != 2) {
            return;
        }

        Vector2 position = new Vector2(args.getFloat(0), args.getFloat(1));

        player.vector2(position);

        player.gameRoom().dispatchExceptOne(
            new JSONObject()
                .element("cmd", RequestCommand.Move)
                .element("avatar", new JSONObject()
                    .element("avatarId", player.id())
                    .element("avatarType", player.type())
                )
                .element("x", position.x)
                .element("y", position.y)
            , player);
    }

}
