package com.asgla.requests.unity.player;

import com.asgla.avatar.player.Player;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.util.RequestCommand;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@DatabaseOpen
public class InventoryLoad implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
        JSONObject pli = new JSONObject()
            .element("cmd", RequestCommand.PlayerInventoryLoad)
            .element("playerId", player.user().id())
            .element("inventory", new JSONArray());

        player.dispatch(pli);
    }

}
