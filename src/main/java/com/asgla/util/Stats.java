package com.asgla.util;


public class Stats {

    protected double baseAttackPower = 0.3;
    protected double baseSpiritPower = 0.3;

    protected double baseBlock = 0.3;
    protected double baseCriticalHit = 0.3;
    protected double baseEvasion = 0.3;
    protected double baseHaste = 0.3;
    protected double baseHit = 0.3;
    protected double baseParry = 0.3;
    protected double baseResist = 0.3;

    protected double attackPower = 0.3;
    protected double spiritPower = 0.3;

    protected double block = 0.3;
    protected double criticalHit = 0.3;
    protected double evasion = 0.3;
    protected double haste = 0.3;
    protected double hit = 0.3;
    protected double parry = 0.3;
    protected double resist = 0.3;

    protected int damage = 0;
    protected double reduction = 0;

    public void resetValues() {
        attackPower = 0;
        spiritPower = 0;
        hit = 0;
        criticalHit = 0.10;
        evasion = 0.06;
        haste = 0;
        block = 0;
        parry = 0.03;
        resist = 0;

        damage = 0;
        reduction = 0;
    }

    //Base stats

    public double baseAttackPower() {
        return baseAttackPower;
    }

    public double baseSpiritPower() {
        return baseSpiritPower;
    }

    public double baseBlock() {
        return baseBlock;
    }

    public double baseCriticalHit() {
        return baseCriticalHit;
    }

    public double baseEvasion() {
        return baseEvasion;
    }

    public double baseHaste() {
        return baseHaste;
    }

    public double baseHit() {
        return baseHit;
    }

    public double baseParry() {
        return baseParry;
    }

    public double baseResist() {
        return baseResist;
    }

    //Avatar stats

    public double attackPower() {
        return attackPower;
    }

    public double spiritPower() {
        return spiritPower;
    }

    public double block() {
        return block;
    }

    public double criticalHit() {
        return criticalHit;
    }

    public double evasion() {
        return evasion;
    }

    public double haste() {
        return haste;
    }

    public double hit() {
        return hit;
    }

    public double parry() {
        return parry;
    }

    public double resist() {
        return resist;
    }

    public int damage() {
        return damage;
    }

    public double reduction() {
        return reduction;
    }

    public void damage(int damage) {
        this.damage = damage;
    }

}