package com.asgla.requests.unity;

import com.asgla.avatar.player.Player;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DatabaseOpen
public class Game implements IRequest {

    private static final Logger log = LoggerFactory.getLogger(Game.class);

    @Override
    public void onRequest(Player player, RequestArgs args) {
        JSONObject game = new JSONObject()
            .element("cmd", 10)
            .element("player", player.properties());

        player.dispatch(game);
    }

}
