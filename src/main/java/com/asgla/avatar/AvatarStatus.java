package com.asgla.avatar;

public abstract class AvatarStatus {

    private AvatarStatusValue health;
    private AvatarStatusValue energy;

    public AvatarStatusValue health() {
        return health;
    }

    public void health(AvatarStatusValue health) {
        this.health = health;
    }

    public AvatarStatusValue energy() {
        return energy;
    }

    public void energy(AvatarStatusValue energy) {
        this.energy = energy;
    }

}
