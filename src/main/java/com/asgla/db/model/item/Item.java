package com.asgla.db.model.item;

import com.asgla.db.model.type.TypeItem;
import com.asgla.db.model.type.TypeRarity;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("items")
public class Item extends Model {

    public Integer typeItemId() {
        return getInteger("type_item_id");
    }

    public Integer typeRarityId() {
        return getInteger("type_rarity_id");
    }

    public Integer stats_id() {
        return getInteger("stats_id");
    }

    public String name() {
        return getString("name");
    }

    public String description() {
        return getString("description");
    }

    public String bundle() {
        return getString("bundle");
    }

    public String asset() {
        return getString("asset");
    }

    public TypeItem typeItem() {
        return TypeItem.findById(typeItemId());
    }

    public TypeRarity typeRarity() {
        return TypeRarity.findById(typeRarityId());
    }

}
