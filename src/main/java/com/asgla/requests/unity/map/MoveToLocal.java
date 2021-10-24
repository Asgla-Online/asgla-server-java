package com.asgla.requests.unity.map;

import com.asgla.avatar.player.Player;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.db.model.area.AreaLocal;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;

@DatabaseOpen
public class MoveToLocal implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
        if (args.length != 2) {
            return;
        }

        String area = args.getStr(0);
        String position = args.getStr(1);

        //TODO: end combat

        player.moveToLocal(AreaLocal.findFirst("area_id = ? AND name = ?", player.gameRoom().data().getId(), area), position, true);
    }

}
