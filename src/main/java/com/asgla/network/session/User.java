package com.asgla.network.session;

import com.asgla.Main;
import com.asgla.api.PlayerNetwork;
import com.asgla.avatar.player.Player;
import com.asgla.data.GameRoom;
import com.asgla.db.Database;
import com.asgla.util.RequestCommand;
import io.netty.util.AttributeKey;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.asgla.controller.RoomController.roomManager;
import static com.asgla.controller.UserController.userManager;

public class User {

    public static final AttributeKey<User> PLAYER_KEY = AttributeKey.valueOf("User");
    private static final AtomicInteger count = new AtomicInteger(0);

    private final int userId;
    private final PlayerNetwork network;

    private final LinkedList<Room> roomsConnected = new LinkedList<>();

    private Player player;
    private String name;
    private String ip;

    public User(PlayerNetwork network) {
        this.userId = count.getAndIncrement();
        this.network = network;
    }

    public int id() {
        return this.userId;
    }

    public Player player() {
        return player;
    }

    public void player(Player player) {
        this.player = player;
    }

    public void name(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void ip(String ip) {
        this.ip = ip;
    }

    public String ip() {
        return this.ip;
    }

    public PlayerNetwork network() {
        return network;
    }

    public void room(Room r) {
        synchronized (this.roomsConnected) {
            this.roomsConnected.add(r);
        }
    }

    public void removeRoom(Room r) {
        synchronized (this.roomsConnected) {
            this.roomsConnected.remove(r);
        }
    }

    public int room() {
        try {
            Room r = this.roomsConnected.get(0);
            return r.id();
        } catch (IndexOutOfBoundsException ignored) {
        }

        return -1;
    }

    public void disconnect() {
        Database.open();

        Player player = player();

        if (player != null) {
            GameRoom gameRoom = player.gameRoom();

            if (gameRoom != null) {

                player.gameRoom().dispatchExceptOne(
                    new JSONObject()
                        .element("Cmd", RequestCommand.LeaveMap)
                        .element("PlayerID", player.id()),
                    player
                );

                gameRoom.room().removeUser(this);

                if (gameRoom.room().userCount() <= 0) {
                    roomManager().removeRoom(gameRoom.room().id());
                }
            }

            //TODO: notification

            player.onDeath();
        }

        if (userManager().getUserById(id()) != null) {
            userManager().removeUser(this);
        }

        Main.serverController().server().decreaseCount();

        Database.close();
    }

}
