package com.asgla.db.model.character;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("characters_attributes")
@IdName("character_id")
public class CharacterAttribute extends Model {

    public String gold() {
        return getString("gold");
    }

    public String diamond() {
        return getString("diamond");
    }

    public String gender() {
        return getString("gender");
    }

    public String access() {
        return getString("access");
    }

    public Integer level() {
        return getInteger("level");
    }

    public String experience() {
        return getString("experience");
    }

    public String bagSlot() {
        return getString("bag_slot");
    }

    public String colorAccessory() {
        return getString("color_accessory");
    }

    public String colorBase() {
        return getString("color_base");
    }

    public String colorTrim() {
        return getString("color_trim");
    }

    public String colorSkin() {
        return getString("color_skin");
    }

    public String colorEye() {
        return getString("color_eye");
    }

    public String colorHair() {
        return getString("color_hair");
    }

    public String colorMouth() {
        return getString("color_mouth");
    }

    public String colorNose() {
        return getString("color_nose");
    }

    public Character character() {
        return Character.findById(getId());
    }

}
