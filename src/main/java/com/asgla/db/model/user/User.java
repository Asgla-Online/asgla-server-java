package com.asgla.db.model.user;

import com.asgla.db.model.character.Character;
import com.asgla.db.model.country.Country;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;

@Table("users")
public class User extends Model {

    public String username() {
        return getString("username");
    }

    public String email() {
        return getString("email");
    }

    public String password() {
        return getString("password");
    }

    public Integer accessLevelId() {
        return getInteger("access_level_id");
    }

    public String countryCode() {
        return getString("country_code");
    }

    public Country country() {
        return Country.findById(countryCode());
    }

    public List<Character> characters() {
        return Character.find("user_id = ?", getId());
    }

}
