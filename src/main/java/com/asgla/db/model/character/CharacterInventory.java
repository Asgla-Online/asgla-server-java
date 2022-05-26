package com.asgla.db.model.character;

import com.asgla.db.model.item.Item;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Timestamp;

@Table("characters_inventory")
public class CharacterInventory extends Model {

    public Integer characterId() {
        return getInteger("character_id");
    }

    public Integer itemId() {
        return getInteger("item_id");
    }

    public boolean isEquipped() {
        return getBoolean("is_equipped");
    }

    public Timestamp purchasedAt() {
        return getTimestamp("purchased_at");
    }

    public Timestamp updatedAt() {
        return getTimestamp("updated_at");
    }

    public Timestamp deletedAt() {
        return getTimestamp("deleted_at");
    }

    public Character character() {
        return Character.findById(characterId());
    }

    public Boolean isDeleted() {
        return deletedAt() != null;
    }

    public CharacterInventory equipped(boolean value) {
        return setBoolean("is_equipped", value);
    }

    public Item item() {
        return Item.findById(itemId());
    }

}
