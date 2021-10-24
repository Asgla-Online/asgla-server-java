package com.asgla.data;

import com.asgla.avatar.player.Player;
import com.asgla.db.model.area.Area;
import com.asgla.exceptions.AreaNotFoundException;
import com.asgla.exceptions.RoomCreationException;
import com.asgla.network.session.Room;
import com.asgla.network.session.User;
import com.asgla.util.IDispatchable;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.asgla.controller.RoomController.roomManager;

public class GameRoom implements IDispatchable {

    private static final Logger log = LoggerFactory.getLogger(GameRoom.class);

    public static final GameRoom NONE = new GameRoom();

    static {
        NONE.room(new Room("", 0));
    }

    public static GameRoom look(String areaName) throws RoomCreationException, AreaNotFoundException {
        int i = 1;

        while (i <= 1000) {
            GameRoom gameRoom = find(areaName + " " + i);

            log.info(gameRoom == null ? "null 1" : "ok 1 " + gameRoom.databaseId());

            if (gameRoom.isNotFull()) {
                return gameRoom;
            } else if (gameRoom.equals(GameRoom.NONE)) {
                break;
            }
            i++;
        }

        log.info("ok 2");

        return create(areaName, i);
    }

    public static GameRoom find(int id) {
        Room room = roomManager().getRoom(id);
        return room == null ? GameRoom.NONE : room.gameRoom();
    }

    public static GameRoom find(String name) {
        Room room = roomManager().getRoomByName(name.toLowerCase(Locale.US));
        return room == null ? GameRoom.NONE : room.gameRoom();
    }

    public static GameRoom find(String areaName, int key) throws AreaNotFoundException {
        Room room = roomManager().getRoomByName((areaName + " " + key).toLowerCase(Locale.US));
        return room == null ? create(areaName, key) : room.gameRoom();
    }

    private static GameRoom create(String name, int key) throws AreaNotFoundException {
        Area area = Area.findFirst("name = ?", name);

        if (area == null) {
            throw new AreaNotFoundException("Can not find area");
        }

        GameRoom gameRoom = new GameRoom();

        gameRoom.room(new Room("%s %d".formatted(name.toLowerCase(Locale.US), key), area.maxPlayers()));
        gameRoom.room().gameRoom(gameRoom);

        roomManager().addRoom(gameRoom.room());

        gameRoom.databaseId = ((Long) area.getId()).intValue();

        return gameRoom;
    }

    private Room room;

    private int databaseId = 0;

    public Room room() {
        return room;
    }

    public void room(Room room) {
        this.room = room;
    }

    public int databaseId() {
        return databaseId;
    }

    public void databaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public boolean doesExist() {
        return !this.equals(GameRoom.NONE);
    }

    public boolean isFull() {
        return room().userCount() >= room().maxUsers();
    }

    public boolean isNotFull() {
        return !isFull();
    }

    public Area data() {
        return Area.findById(databaseId());
    }

    public int id() {
        return room().id();
    }

    public String name() {
        return room().name();
    }

    public Set<Player> playerByAreaName(String name) {
        return Arrays.stream(room().getAllUsers()).map(User::player).filter(player -> player.gameRoom().data().name().equals(name)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void dispatchExceptOne(JSONObject jsonObject, Player player) {
        Arrays.stream(room().getAllUsers()).map(User::player).filter(roomUser -> !roomUser.equals(player)).forEachOrdered(roomUser -> roomUser.dispatch(jsonObject));
    }

    /**
     * Dispatches a JSON message to this object.
     * <p>
     *
     * @param jsonObject     the JSON object
     */
    @Override
    public void dispatch(JSONObject jsonObject) {
        Arrays.stream(room().getAllUsers()).forEachOrdered(user -> user.player().dispatch(jsonObject));
    }

}
