package com.asgla.db.model.aura;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("auras")
public class Aura extends Model {

    public String name() {
        return getString("name");
    }

}
