package com.asgla.db.model.character;

import com.asgla.db.model.user.User;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;

@Table("characters")
public class Character extends Model {

    public String name() {
        return getString("name");
    }

    public Integer userId() {
        return getInteger("user_id");
    }

    public User user() {
        return User.findById(userId());
    }

    public List<CharacterInventory> characterInventory() {
        return CharacterInventory.find("UserID = ?", getId());
    }

}
