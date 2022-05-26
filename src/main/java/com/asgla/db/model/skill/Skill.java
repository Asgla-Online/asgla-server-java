package com.asgla.db.model.skill;

import com.asgla.handlers.avatar.combat.skill.SkillAbstract;
import org.apache.commons.lang.NotImplementedException;
import org.javalite.activejdbc.Model;

import java.util.List;

public class Skill extends Model {

    public double damage() {
        return getDouble("damage");
    }

    public String target() {
        return getString("target");
    }

    public String type() {
        return getString("type");
    }

    public SkillAbstract handler() {
        throw new NotImplementedException();
    }

    public List<SkillAura> skillAuras() {
        return SkillAura.find("skill_id = ?", getId());
    }

}
