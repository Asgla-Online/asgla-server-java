package com.asgla.requests.unity;

import com.asgla.avatar.player.Player;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import net.sf.json.JSONObject;

public class Ping implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
        player.dispatch(
            new JSONObject().element("cmd", 9)
        );
    }

}
