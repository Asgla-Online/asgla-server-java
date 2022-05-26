package com.asgla.handlers.avatar.combat.skill;

import com.asgla.avatar.Avatar;
import com.asgla.avatar.combat.CombatAura;
import com.asgla.db.model.skill.Skill;
import com.asgla.db.model.skill.SkillAura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class SkillAbstract {

    public abstract void onSkill();

    public static String NONE = "none";
    public static String HIT = "hit";
    public static String CRITICAL = "critical";
    public static String MISS = "miss";
    public static String DODGE = "dodge";

    private Skill skill;

    private Avatar source;
    private Avatar target;
    private String type;

    private int damage;

    public List<String> types = Arrays.asList(HIT, CRITICAL, NONE);

    public List<SkillAura> randomTarget = new ArrayList<>();
    public List<SkillAura> randomSelf = new ArrayList<>();

    public void applyAura() {
        if (!getType().equals(MISS) && !getType().equals(DODGE)) {
            for (SkillAura skillAura : getSkill().skillAuras()) {
                if (skillAura.isRandom() && skillAura.isEnemies()) {
                    randomTarget.add(skillAura);
                    continue;
                }

                if (skillAura.isRandom() && skillAura.isSelf()) {
                    randomSelf.add(skillAura);
                    continue;
                }

                if (validateAura(skillAura)) {
                    CombatAura.create(getSource(), skillAura.isSelf() ? getSource() : getTarget(), getTarget(), skillAura, getDamage());
                }
            }
        }
    }

    public boolean validateAura(SkillAura skillAura) {
        return skillAura != null && !(Math.random() > skillAura.chance());
    }

    public int generateDamage() {
        int min = 0;
        int max = 0;

        switch (getSkill().type()) {
            case "physical" -> {
                max = getSource().stats().maximumAttackDamage();
                min = getSource().stats().minimumAttackDamage();
            }
            case "magic" -> {
                max = getSource().stats().maximumMagicDamage();
                min = getSource().stats().minimumMagicDamage();
            }
            case "physical_magic" -> {
                max = (getSource().stats().maximumAttackDamage() >> 1) + getSource().stats().maximumMagicDamage();
                min = (getSource().stats().minimumAttackDamage() >> 1) + getSource().stats().minimumMagicDamage();
            }
            case "magic_physical" -> {
                max = (getSource().stats().maximumMagicDamage() >> 1) + getSource().stats().maximumAttackDamage();
                min = (getSource().stats().minimumMagicDamage() >> 1) + getSource().stats().minimumAttackDamage();
            }
        }

        int delta = 1 + Math.abs(max - min);

        return (int) ((new Random().nextInt(delta) + min) * getSkill().damage());
    }

    public Skill getSkill() {
        return skill;
    }

    public SkillAbstract setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public Avatar getSource() {
        return source;
    }

    public SkillAbstract setSource(Avatar source) {
        this.source = source;
        return this;
    }

    public Avatar getTarget() {
        return target;
    }

    public SkillAbstract setTarget(Avatar target) {
        this.target = target;
        return this;
    }

    public String getType() {
        return type;
    }

    public SkillAbstract setType(String type) {
        this.type = type;
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public SkillAbstract setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public SkillAbstract setDamageByMultiplier(double multiplier) {
        setDamage((int) (getDamage() * multiplier));
        return this;
    }
}
