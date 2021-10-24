package com.asgla.requests.unity.player;

import com.asgla.avatar.player.Player;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.util.RequestCommand;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@DatabaseOpen
public class DataLoad implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
        if (args.length < 1) {
            return;
        }

        JSONArray players = new JSONArray();

        args.list().stream().mapToInt(Integer::parseInt).mapToObj(Player::findById).forEachOrdered(target -> {
            JSONObject data = target.properties();

            data.element("part", target.inventory().json());

            if (target.equals(player)) {
                data.element("isControlling", true);
            }

            players.add(new JSONObject()
                .element("sata", data)
                .element("stats", new JSONObject()
                    .element("health", target.status().health().getValue())
                    .element("healthMax", target.status().health().getValueMax())
                    .element("energy", target.status().energy().getValue())
                    .element("energyMax", target.status().energy().getValueMax())
                )
            );
        });

        player.dispatch(new JSONObject()
            .element("cmd", RequestCommand.PlayerDataLoad)
            .element("players", players)
        );
    }

}
