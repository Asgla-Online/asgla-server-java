package com.asgla.controller;

import com.asgla.network.session.User;
import io.netty.channel.Channel;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UserController {

    private static final UserController _instance = new UserController();

    private final ConcurrentHashMap<Integer, User> usersConnected;

    private int highestCount = 0;

    public UserController() {
        this.usersConnected = new ConcurrentHashMap<>();
    }

    public static UserController userManager() {
        return _instance;
    }

    public void removeUser(User user) {
        this.usersConnected.remove(user.id());
    }

    public void addUser(User user) {
        this.usersConnected.put(user.id(), user);

        if (usersConnected.size() > highestCount) {
            highestCount = usersConnected.size();
        }
    }

    public User getUserById(Integer i) {
        synchronized (this.usersConnected) {
            for (User user : this.usersConnected.values()) {
                if (user.id() == i) {
                    return user;
                }
            }
        }

        return null;
    }

    public User getUserByName(String name) {
        synchronized (this.usersConnected) {
            for (User user : this.usersConnected.values()) {
                if (user.name().equalsIgnoreCase(name)) {
                    return user;
                }
            }
        }

        return null;
    }

    public LinkedList<Channel> getChannelList() {
        return usersConnected.values().stream().filter(Objects::nonNull).map(user -> user.network().getChannel()).collect(Collectors.toCollection(LinkedList::new));
    }

    public int getUserCount() {
        return this.usersConnected.size();
    }

    public int getUserHighestCount() {
        return this.highestCount;
    }

}
