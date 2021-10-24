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
        if (args.length < 1) return;

        JSONArray players = new JSONArray();

        for (String id : args.list()) {
            int userId = Integer.parseInt(id);

            Player target = Player.findById(userId);

            JSONObject data = target.properties();

            //data.element("Part", target.equipment().json());

            if (target.equals(player))
                data.element("Controlling", true);

            players.add(new JSONObject()
                .element("Data", data)
                .element("Stats", new JSONObject()
                    .element("Health", target.status().health().getValue())
                    .element("HealthMax", target.status().health().getValueMax())
                    .element("Energy", target.status().energy().getValue())
                    .element("EnergyMax", target.status().energy().getValueMax())
                )
            );
        }

        player.dispatch(new JSONObject()
            .element("cmd", RequestCommand.PlayerDataLoad)
            .element("Players", players)
        );
    }

}
