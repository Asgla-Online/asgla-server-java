package com.asgla.api;

import io.netty.channel.Channel;
import net.sf.json.JSONObject;

import java.util.LinkedList;

public abstract class PlayerNetwork {

    private int connectionId;

    public PlayerNetwork(int connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Gets the connection id.
     *
     * @return the connection id
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the connection id.
     *
     * @param connectionId the new connection id
     */
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Close the connection.
     */
    public abstract void close();

    /**
     * Flush all json written to socket.
     */
    public abstract void flush();

    public abstract void send(JSONObject jsonObject);

    public abstract void sendQueued(JSONObject jsonObject);

    public abstract void sendExcept(JSONObject jsonObject, LinkedList<Channel> channels);

    public abstract Channel getChannel();

}
