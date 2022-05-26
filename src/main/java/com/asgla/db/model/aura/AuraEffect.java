package com.asgla.db.model.aura;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("auras_effects")
public class AuraEffect extends Model {

    public String stat() {
        return getString("stat");
    }

    public String operator() {
        return getString("operator");
    }

    public double value() {
        return getDouble("value");
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

}
