package com.asgla.avatar;

import com.asgla.avatar.combat.CombatAura;
import com.asgla.data.GameRoom;
import com.asgla.db.model.area.AreaLocal;
import com.asgla.util.AvatarType;
import com.asgla.util.Vector2;
import org.apache.commons.lang.NotImplementedException;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class Avatar implements IAvatar {

    private AvatarType avatarType;

    protected AreaLocal area;//..
    protected String point;//..

    private Vector2 position = new Vector2(0, 0);

    private AreaLocal local;

    private final Set<Avatar> targets = new CopyOnWriteArraySet<>();

    public abstract long databaseId();

    public abstract int id();

    public abstract AvatarType type();

    public Vector2 vector2() {
        return position;
    }

    public void vector2(Vector2 position) {
        this.position = position;
    }

    public AreaLocal local() {
        return local;
    }

    public void local(AreaLocal local) {
        this.local = local;
    }

    public AvatarStats stats() {
        throw new NotImplementedException();
    }

    public AvatarStatus status() {
        throw new NotImplementedException();
    }

    public Set<Avatar> targets() {
        return targets;
    }

    //-------------

    public GameRoom gameRoom() {
        throw new NotImplementedException();
    }

    public void attack(Avatar target, int damage) {
        throw new NotImplementedException();
    }

    public Set<CombatAura> auras() {
        throw new NotImplementedException();
    }

    //-------------

    @Override
    public void onAttack() {
        throw new NotImplementedException();
    }

    @Override
    public void onDodge() {
        throw new NotImplementedException();
    }

    @Override
    public void onMiss() {
        throw new NotImplementedException();
    }

    @Override
    public void onCritic() {
        throw new NotImplementedException();
    }

    @Override
    public void onHit() {
        throw new NotImplementedException();
    }

    @Override
    public void onDamageTaken() {
        throw new NotImplementedException();
    }

    @Override
    public void onDeath() {
        throw new NotImplementedException();
    }

    @Override
    public void onKill() {
        throw new NotImplementedException();
    }

    @Override
    public void onComplete() {
        throw new NotImplementedException();
    }

    @Override
    public void onSpawn() {
        throw new NotImplementedException();
    }

}
