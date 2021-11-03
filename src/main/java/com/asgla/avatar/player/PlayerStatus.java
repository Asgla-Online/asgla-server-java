package com.asgla.avatar.player;

import com.asgla.avatar.AvatarStatus;
import com.asgla.avatar.AvatarStatusValue;

public class PlayerStatus extends AvatarStatus {

    private final Player player;

    public PlayerStatus(Player player) {
        this.player = player;

        this.health(new AvatarStatusValue(5000));
        this.energy(new AvatarStatusValue(3000));
    }

}
