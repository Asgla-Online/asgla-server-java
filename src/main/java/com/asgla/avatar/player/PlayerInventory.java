package com.asgla.avatar.player;

import com.asgla.db.model.character.CharacterInventory;
import com.asgla.db.model.item.Item;
import com.asgla.util.RequestCommand;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class PlayerInventory {

    private final Player player;

    public PlayerInventory(Player player) {
        this.player = player;
    }

    public void equip(CharacterInventory inventory) {
        if (inventory == null)
            return;

        Item item =  inventory.item();

        player.gameRoom().dispatch(new JSONObject()
            .element("cmd", RequestCommand.EquipPart)
            .element("PlayerID", player.user().id())
            .element("Type", item.typeItem().json())
            .element("Bundle", item.bundle())
            .element("Asset", item.asset()));

        inventory.equipped(true);

        player.stats().update();
    }

    public JSONArray json() {
        JSONArray json = new JSONArray();

        List<CharacterInventory> characterInventoryList = CharacterInventory.find("character_id = ? and is_equipped = ?", player.databaseId(), true);

        characterInventoryList.stream().map(characterInventory -> doTheBundleThing(characterInventory.item().typeItem().json(), characterInventory.item().bundle(), characterInventory.item().asset())).forEach(json::element);

        return json;
    }

    private JSONObject doTheBundleThing(JSONObject type, String bundle, String asset) {
        return new JSONObject()
            .element("Type", type)
            .element("Bundle", bundle)
            .element("Asset", asset);
    }

}
