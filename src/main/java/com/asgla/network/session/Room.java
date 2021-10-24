package com.asgla.network.session;

import com.asgla.data.GameRoom;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.asgla.controller.UserController.userManager;

public class Room {

    private static final AtomicInteger count = new AtomicInteger(3);

    private final ConcurrentHashMap<Integer, User> roomUsers;

    public final Object roomSync = new Object();

    private final int id;
    private final String name;
    private final int maxUsers;

    private GameRoom gameRoom;

    public Room(String name, int maxUsers) {
        this.id = count.getAndIncrement();
        this.name = name;
        this.maxUsers = maxUsers;

        this.roomUsers = new ConcurrentHashMap<>();
    }

    public int id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public int maxUsers() {
        return this.maxUsers;
    }

    public GameRoom gameRoom() {
        return gameRoom;
    }

    public void gameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public int userCount() {
        synchronized (this.roomSync) {
            return this.roomUsers.size();
        }
    }

    public void addUser(User user) {
        synchronized (this.roomSync) {
            if (!this.roomUsers.containsKey(user.id())) {
                user.room(this);

                this.roomUsers.put(user.id(), user);
            }
        }
    }

    public boolean contains(User user) {
        synchronized (this.roomSync) {
            return this.roomUsers.containsKey(user.id());
        }
    }

    public void removeUser(User user) {
        synchronized (this.roomSync) {
            if (this.roomUsers.containsKey(user.id())) {
                user.removeRoom(this);
                this.roomUsers.remove(user.id());
            }
        }
    }

    public User[] getAllUsers() {
        ArrayList<User> tempList;

        synchronized (this.roomSync) {
            tempList = this.roomUsers.keySet().stream().mapToInt(id -> id).mapToObj(id -> userManager().getUserById(id)).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        }

        if (tempList.size() == 0) {
            return new User[0];
        }

        Object[] tempArr = tempList.toArray();
        User[] allUsers = new User[tempArr.length];

        System.arraycopy(tempArr, 0, allUsers, 0, allUsers.length);

        return allUsers;
    }

    public User[] getAllUsersButOne(Integer excludedUserId) {
        if (excludedUserId == null) {
            return this.getAllUsers();
        }

        User[] allUsers;

        boolean exist;

        synchronized (this.roomSync) {
            exist = this.roomUsers.containsKey(excludedUserId);

            if (!exist) {
                return this.getAllUsers();
            }

            allUsers = new User[this.roomUsers.size() - 1];

            int c = 0;

            for (Integer userId : this.roomUsers.keySet()) {
                User user = userManager().getUserById(userId);

                if (userId.equals(excludedUserId)) {
                    continue;
                }

                allUsers[c++] = user;
            }
        }
        return allUsers;
    }

    public LinkedList<Channel> getChannelList() {
        return Arrays.stream(this.getAllUsers()).filter(Objects::nonNull).map(user -> user.network().getChannel()).collect(Collectors.toCollection(LinkedList::new));
    }

}
