package com.asgla.network;

import com.asgla.api.PlayerNetwork;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class NettyPlayerNetwork extends PlayerNetwork {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NettyPlayerNetwork.class);

    private final Channel channel;

    public NettyPlayerNetwork(Channel channel, int connectionId) {
        super(connectionId);
        this.channel = channel;
    }

    /**
     * Close the connection.
     */
    @Override
    public void close() {
        channel.close();
    }

    /**
     * Flush all json written to socket.
     */
    @Override
    public void flush() {
        channel.flush();
    }

    @Override
    public void send(JSONObject jsonObject) {
        log.info("Sending > {}", jsonObject);
        channel.writeAndFlush(new TextWebSocketFrame(jsonObject.toString()), channel.voidPromise());
    }

    @Override
    public void sendQueued(JSONObject jsonObject) {
        channel.write(new TextWebSocketFrame(jsonObject.toString()), channel.voidPromise());
    }

    @Override
    public void sendExcept(JSONObject jsonObject, LinkedList<Channel> channels) {
        channels.stream().filter(channel -> !channel.equals(this.channel)).forEach(channel1 -> channel1.writeAndFlush(new TextWebSocketFrame(jsonObject.toString()), channel1.voidPromise()));
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

}
