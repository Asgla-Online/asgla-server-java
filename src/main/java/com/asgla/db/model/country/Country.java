package com.asgla.db.model.country;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("countries_code")
@IdName("code")
public class Country extends Model {

    public String code() {
        return getString("code");
    }

    public String name() {
        return getString("name");
    }

}
