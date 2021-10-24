package com.asgla.requests.unity.map;

import com.asgla.avatar.player.Player;
import com.asgla.data.GameRoom;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.util.Vector2;

@DatabaseOpen
public class Join implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
        if (args.length != 3) {
            return;
        }

        String name = args.getStr(0);

        String area = args.getStr(1);
        String position = args.getStr(2);

        player.vector2(new Vector2());

        try {
            GameRoom gameRoom = GameRoom.look(name);
            player.join(gameRoom, area, position);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
