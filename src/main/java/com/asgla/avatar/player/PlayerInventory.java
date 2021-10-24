package com.asgla.avatar.player;

import com.asgla.db.model.character.CharacterInventory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class PlayerInventory {

    private final Player player;

    public PlayerInventory(Player player) {
        this.player = player;
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
