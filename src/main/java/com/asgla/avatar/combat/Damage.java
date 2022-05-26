package com.asgla.avatar.combat;

import com.asgla.avatar.Avatar;
import com.asgla.db.model.skill.Skill;
import com.asgla.handlers.avatar.combat.skill.SkillAbstract;
import com.asgla.util.RandomNumber;

import java.util.Random;

public class Damage {

    private static final Random random = new Random();

    private SkillAbstract skillAbstract;

    public Damage(Skill skill, Avatar source, Avatar target) {
        skillAbstract = skill.handler()
            .setSkill(skill)
            .setSource(source)
            .setTarget(target)
            .setDamage(skillAbstract.generateDamage());

        double criticalChance = source.stats().criticalHit();
        double dodgeChance = target.stats().evasion();
        double missChance = source.stats().hit();

        boolean critical = random.nextDouble() < criticalChance;
        boolean dodge = false;
        boolean miss = false;

        if (!skill.target().equals("s")) {
            dodge = random.nextDouble() < dodgeChance;
            miss = random.nextDouble() < missChance;
        }

        if (skillAbstract.getDamage() > 0 && dodge || missChance > 1) {
            skillAbstract
                .setDamage(0)
                .setType(SkillAbstract.DODGE);
        } else if (skillAbstract.getDamage() > 0 && miss) {
            skillAbstract
                .setDamage(0)
                .setType(SkillAbstract.MISS);
        } else if (critical) {
            skillAbstract
                .setDamageByMultiplier(RandomNumber.doubleInRange(1D, 2D))
                .setType(SkillAbstract.CRITICAL);

            source.status().energy().increaseByPercent(10);
        } else {
            skillAbstract.setType(SkillAbstract.HIT);
        }

        skillAbstract.onSkill();

        //TODO: Evaluate auras

        source.attack(target, skillAbstract.getDamage());
    }

}
