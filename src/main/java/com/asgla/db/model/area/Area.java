package com.asgla.db.model.area;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;
import java.util.stream.Collectors;

@Cached
@Table("areas")
public class Area extends Model {

    public String name() {
        return getString("name");
    }

    public String bundle() {
        return getString("bundle");
    }

    public String asset() {
        return getString("asset");
    }

    public Integer maxPlayers() {
        return getInteger("max_players");
    }

    public List<AreaLocal> areaLocals() {
        return AreaLocal.find("area_id = ?", getId());
    }

    public JSONArray jsonLocals() {
        return (JSONArray) areaLocals().stream().map(AreaLocal::jsonObject).collect(Collectors.toCollection(JSONArray::new));
    }

    public JSONObject jsonObject() {
        return new JSONObject()
            .element("databaseID", getId())
            .element("name", name())
            .element("bundle", bundle())
            .element("asset", asset())
            .element("maxPlayers", maxPlayers())
            .element("locals", jsonLocals());
    }

}
