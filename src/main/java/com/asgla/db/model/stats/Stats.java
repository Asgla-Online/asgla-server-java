package com.asgla.db.model.stats;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("stats")
public class Stats extends Model {

    public Integer attackPower() {
        return getInteger("attack_power");
    }

    public Integer magicPower() {
        return getInteger("magic_power");
    }

    public Integer hit() {
        return getInteger("hit");
    }

    public Integer criticalHit() {
        return getInteger("critical_hit");
    }

    public Integer evasion() {
        return getInteger("evasion");
    }

    public Integer haste() {
        return getInteger("haste");
    }

    public Integer block() {
        return getInteger("block");
    }

    public Integer parry() {
        return getInteger("parry");
    }

    public Integer resist() {
        return getInteger("resist");
    }

}
