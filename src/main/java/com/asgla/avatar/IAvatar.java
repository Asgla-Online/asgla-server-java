package com.asgla.avatar;

public interface IAvatar {

    //Combat
    void onAttack();

    void onDodge();

    void onMiss();

    void onCritic();

    void onHit();

    void onDamageTaken();

    void onDeath();

    void onKill();

    void onComplete();

    boolean onSpawn();

}
