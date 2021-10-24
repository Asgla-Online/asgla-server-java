package com.asgla.requests.unity.player;

import com.asgla.avatar.player.Player;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;

@DatabaseOpen
public class EquipItem implements IRequest {

    @Override
    public void onRequest(Player player, RequestArgs args) {
    }

}
