package com.asgla.avatar.player;

import com.asgla.avatar.AvatarStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerStats extends AvatarStats {

    private static final Logger log = LoggerFactory.getLogger(PlayerStats.class);

    private final Player player;

    private int weaponDps = 1;
    private int maxAttackDmg = 1000;
    private int minAttackDmg = 100;

    private int magicDps = 1;
    private int maxMagicDmg = 2000;
    private int minMagicDmg = 200;

    public PlayerStats(Player player) {
        this.player = player;
    }

    @Override
    public double reduction() {
        return reduction;
    }

    @Override
    public int weaponDps() {
        return weaponDps;
    }

    @Override
    public int maximumAttackDamage() {
        return maxAttackDmg;
    }

    @Override
    public int minimumAttackDamage() {
        return minAttackDmg;
    }

    @Override
    public int magicDps() {
        return magicDps;
    }

    @Override
    public int maximumMagicDamage() {
        return maxMagicDmg;
    }

    @Override
    public int minimumMagicDamage() {
        return minMagicDmg;
    }

    @Override
    public void update() {

    }

}
