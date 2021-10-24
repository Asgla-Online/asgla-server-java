package com.asgla.avatar.player;

import com.asgla.avatar.Avatar;
import com.asgla.data.GameRoom;
import com.asgla.db.model.area.AreaLocal;
import com.asgla.db.model.character.Character;
import com.asgla.db.model.character.CharacterAttribute;
import com.asgla.network.session.User;
import com.asgla.util.RequestCommand;
import com.asgla.util.ServerMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

import static com.asgla.controller.RoomController.roomManager;
import static com.asgla.controller.UserController.userManager;

public class Player extends Avatar {

    public static final Player NONE = new Player();

    public static Player findById(int id) {
        User user = userManager().getUserById(id);
        return user == null ? Player.NONE : user.player();
    }

    public static Player findByName(String name) {
        User user = userManager().getUserByName(name.toLowerCase(Locale.US));
        return user == null ? Player.NONE : user.player();
    }

    private User user;
    private long databaseId;

    public Player() { }

    public Player(User user, long databaseId) {
        this.user = user;
        this.databaseId = databaseId;

        user.player(this);
    }

    public User user() {
        return user;
    }

    public long databaseId() {
        return databaseId;
    }

    public int id() {
        return user().id();
    }

    public Character character() {
        return Character.findById(databaseId());
    }

    public JSONObject properties() {
        Character character = character();
        CharacterAttribute characterAttribute = character.characterAttribute();

        return new JSONObject()
            .element("playerId", user().id())
            .element("databaseId", databaseId())

            .element("username", user().name())
            .element("gender", characterAttribute.gender())

            //.element("ear", characterAttribute.ear().jsonObject())
            //.element("eye", characterAttribute.eye().jsonObject())
            //.element("hair", characterAttribute.hair().jsonObject())
            //.element("mouth", characterAttribute.mouth().jsonObject())
            //.element("nose", characterAttribute.nose().jsonObject())

            .element("colorSkin", characterAttribute.colorSkin())
            .element("colorEye", characterAttribute.colorEye())
            .element("colorHair", characterAttribute.colorHair())
            .element("colorMouth", characterAttribute.colorMouth())
            .element("colorNose", characterAttribute.colorNose())

            .element("area", new JSONObject()
                .element("area", area.name())
                .element("point", point))

            .element("x", vector2().x)
            .element("y", vector2().y)

            .element("level", characterAttribute.level());

            //.element("state", status().state().intValue())
            //.element("away", isAway());
    }

    public void update(GameRoom gameRoom) {
        gameRoom.dispatchExceptOne(
            new JSONObject()
                .element("cmd", RequestCommand.PlayerUpdate)
                .element("player", this.properties()),
            this
        );
    }

    public void moveToMap(Player player, GameRoom gameRoom) {
        JSONArray players = new JSONArray();

        User[] users = gameRoom.room().getAllUsers();

        Arrays.stream(users).filter(user -> player.user() != user || user.room() != -1).map(User::player).forEach(playerInRoom -> {
            JSONObject playerInRoomProp = playerInRoom.properties();
            if (player.equals(playerInRoom)) {
                playerInRoomProp.element("isControlling", player.equals(playerInRoom));
            }
            players.add(playerInRoomProp);
        });

        JSONObject jsonObject = gameRoom.data().jsonObject();

        jsonObject
            .element("roomId", gameRoom.id())
            .element("players", players);
        //.element("monsters", gameRoom().monsters());
        //.element("npcs", gameRoom().npcs());

        player.dispatch(new JSONObject()
            .element("cmd", RequestCommand.JoinMap)
            .element("area", jsonObject)
        );

        player.dispatch(ServerMessage.json(ServerMessage.GAME, "Joined '%s'", gameRoom.name()));
    }

    public void join(GameRoom room) {
        join(room, "Enter", "Spawn");
    }

    public void join(GameRoom room, String area, String position) {
        if (room.isFull()) {
            //Destination room is full.
            return;
        }
        if (room.equals(gameRoom())) {
            //Cannot join a room you are currently in.
            return;
        }

        moveToLocal(AreaLocal.findFirst("area_id = ? AND name = ?", room.data().getId(), area), position);

        roomManager().joinRoom(user(), room.name());

        moveToMap(this, room);
    }

    public void moveToLocal(AreaLocal area, String point) {
        moveToLocal(area, point, false);
    }

    public void moveToLocal(AreaLocal area, String point, boolean sendUpdate) {
        this.area = area;
        this.point = point;

        //remove targets when player exit area
        for (Avatar a : targets()) {
            a.targets().remove(this);
        }

        if (sendUpdate) {
            gameRoom().dispatchExceptOne(
                new JSONObject()
                    .element("cmd", 11)
                    .element("playerId", user().id())
                    .element("area", area)
                    .element("position", point)
                , this
            );
        }
    }

    @Override
    public GameRoom gameRoom() {
        return GameRoom.find(user().room());
    }

    /**
     * Dispatches a JSON message to this object.
     * <p>
     *
     * @param jsonObject the JSON object
     */
    @Override
    public void dispatch(JSONObject jsonObject) {
        user().network().send(jsonObject);
    }

}
