package com.asgla.db.model.type;

import net.sf.json.JSONObject;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("types_items")
public class TypeItem extends Model {

    public String name() {
        return getString("name");
    }

    public int category() {
        return getInteger("category");
    }

    public int equipment() {
        return getInteger("equipment");
    }

    public int weapon() {
        return getInteger("weapon");
    }

    public String icon() {
        return getString("icon");
    }

    public JSONObject json() {
        return new JSONObject()
            .element("name", name())
            .element("category", category())
            .element("equipment", equipment())
            .element("weapon", weapon())
            .element("icon", icon());
    }

}
