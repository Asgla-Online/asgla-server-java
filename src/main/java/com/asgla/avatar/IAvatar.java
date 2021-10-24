package com.asgla.avatar;

import com.asgla.util.IDispatchable;

public interface IAvatar extends IDispatchable {

    void onAttack();

    void onDodge();

    void onMiss();

    void onCritic();

    void onHit();

    void onDamageTaken();

    void onDeath();

    void onKill();

    void onComplete();

    void onSpawn();

}
