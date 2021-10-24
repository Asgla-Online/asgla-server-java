package com.asgla.controller;

import com.asgla.api.ServerHandler;
import com.asgla.db.model.server.Server;
import com.asgla.util.IDispatchable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.asgla.controller.UserController.userManager;

public class ServerController implements IDispatchable {

    private final ConcurrentHashMap<String, String> allowed = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> blocked = new ConcurrentHashMap<>();

    private Server server = null;

    /**
     * Create the server instance
     */
    public void setup() {
        Server server = Server.findById(1);

        server(server);

        Class<? extends ServerHandler> serverClass = null;
        try {
            serverClass = Class.forName("com.asgla.network.NettyServer").asSubclass(ServerHandler.class);
            Constructor<? extends ServerHandler> serverConstructor = serverClass.getDeclaredConstructor(String.class, Integer.class);

            // Create the server instance
            ServerHandler serverHandler = serverConstructor.newInstance(server.ip(), server.port());

            serverHandler.createSocket();
            serverHandler.bind();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes the server status offline
     */
    public void inactive() {
        server().playersCountReset();

        //TODO: Update character current server
    }

    /**
     * Makes the server status online
     */
    public void active() {
        server().playersCountReset();

        //TODO: Update character current server
    }

    public int allowedCount() {
        return allowed.size();
    }

    public int blockedCount() {
        return blocked.size();
    }

    public boolean isAllowed(String username) {
        return allowed.containsKey(username.toLowerCase());
    }

    public boolean isBanned(SocketAddress sa) {
        String ip = sa.toString().replace("/", "").split(":")[0];

        if (!blocked.containsKey(ip)) {
            return false;
        }

        if (blocked.get(ip) + TimeUnit.MINUTES.toMillis(1) > System.currentTimeMillis()) {
            blocked.remove(ip);
            return false;
        }

        return true;
    }

    public void allow(String username, String token) {
        allowed.put(username.toLowerCase(), token);
    }

    public void banIP(SocketAddress sa, Long time) {
        String ip = sa.toString().replace("/", "").split(":")[0];

        //TODO: Check if equals server ip

        blocked.put(ip, time);
    }

    public void banIP(String sa, Long time) {
        blocked.put(sa, time);
    }

    public void removeAllowed(String username) {
        allowed.remove(username.toLowerCase());
    }

    public void removeBannedIP(SocketAddress sa) {
        blocked.remove(sa.toString().replace("/", "").split(":")[0]);
    }

    public void removeBannedIP(String ip) {
        blocked.remove(ip);
    }

    public void clearBanned() {
        blocked.clear();
    }

    public Server server() {
        return server;
    }

    public void server(Server server) {
        this.server = server;
    }

    /**
     * Dispatches a JSON message to this object.
     * <p>
     *
     * @param jsonObject         the JSON object
     */
    @Override
    public void dispatch(JSONObject jsonObject) {
        userManager().getChannelList().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(jsonObject.toString()), channel.voidPromise()));
    }

}
