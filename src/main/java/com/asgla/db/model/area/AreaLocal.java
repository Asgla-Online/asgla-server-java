package com.asgla.db.model.area;

import net.sf.json.JSONObject;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("areas_local")
public class AreaLocal extends Model {

    public String name() {
        return getString("name");
    }

    public Integer areaId() {
        return getInteger("area_id");
    }

    public boolean isSafe() {
        return getBoolean("is_safe");
    }

    public double scale() {
        return getDouble("scale");
    }

    public double speed() {
        return getDouble("speed");
    }

    public Area area() {
        return Area.findById(areaId());
    }

    public JSONObject jsonObject() {
        return new JSONObject()
            .element("name", name())
            .element("scale", scale())
            .element("speed", speed())
            .element("isSafe", isSafe());
    }

}
