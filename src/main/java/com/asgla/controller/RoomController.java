package com.asgla.controller;

import com.asgla.avatar.player.Player;
import com.asgla.network.session.Room;
import com.asgla.network.session.User;
import com.asgla.util.RequestCommand;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomController {

    private static final RoomController _instance = new RoomController();

    private final Map<Integer, Room> roomList;

    public RoomController() {
        this.roomList = new ConcurrentHashMap<>();
    }

    public static RoomController roomManager() {
        return _instance;
    }

    public void addRoom(Room room) {
        if (!this.roomList.containsKey(room.id())) {
            this.roomList.put(room.id(), room);
        }
    }

    public void removeRoom(int id) {
        this.roomList.remove(id);
    }

    public int getRoomCount() {
        return this.roomList.size();
    }

    public Room getRoom(int roomId) {
        return this.roomList.getOrDefault(roomId, null);
    }

    public Room getRoomByName(String name) {
        return roomList.values().stream().filter(room -> room.name().equals(name)).findFirst().orElse(null);
    }

    public LinkedList<Room> getAllRooms() {
        return new LinkedList<>(roomList.values());
    }

    public void joinRoom(User user, String name) {
        if (user.room() != -1) {
            Room room = getRoom(user.room());

            //User LeaveMap
            user.network().sendExcept(
                new JSONObject()
                    .element("cmd",RequestCommand.LeaveMap)
                    .element("playerId", user.id()),
                room.getChannelList()
            );

            room.removeUser(user);

            if (room.userCount() <= 0) {
                removeRoom(room.id());
            }
        }

        Room room = getRoomByName(name);

        room.addUser(user);

        synchronized (room.roomSync) {
            Player player = user.player();

            player.update(player.gameRoom());
        }
    }

}
