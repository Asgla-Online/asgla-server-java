package com.asgla.requests;

import com.asgla.avatar.player.Player;

public interface IRequest {

    void onRequest(Player player, RequestArgs args);

}

