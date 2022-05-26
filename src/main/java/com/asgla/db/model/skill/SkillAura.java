package com.asgla.db.model.skill;

import com.asgla.db.model.aura.Aura;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("skills_auras")
public class SkillAura extends Model {

    public int skillId() {
        return getInteger("skill_id");
    }

    public int auraId() {
        return getInteger("aura_id");
    }

    public double chance() {
        return getDouble("Chance");
    }

    public boolean isRandom() {
        return getBoolean("IsRandom");
    }

    /**
     * When target is equal to s, target is Self only.
     * When target is equal to e, target are Enemies only.
     * When target is equal to p, target is Party only.
     * When target is equal to a, target are Allies only.
     *
     * @return Target required
     */
    public String target() {
        return getString("target");
    }

    public Skill skill() {
        return Skill.findById(skillId());
    }

    public Aura aura() {
        return Aura.findById(auraId());
    }

    public boolean isSelf() {
        return target().equals("s");
    }

    public boolean isEnemies() {
        return target().equals("e");
    }

    public boolean isParty() {
        return target().equals("p");
    }

    public boolean isAllies() {
        return target().equals("a");
    }

}
