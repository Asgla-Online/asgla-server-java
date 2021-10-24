package com.asgla.requests.unity.map;

import com.asgla.avatar.player.Player;
import com.asgla.data.GameRoom;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DatabaseOpen
public class JoinFirst implements IRequest {

    private static final Logger log = LoggerFactory.getLogger(JoinFirst.class);

    @Override
    public void onRequest(Player player, RequestArgs args) {
        try {
            GameRoom gameRoom = GameRoom.look("start");
            player.join(gameRoom);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
