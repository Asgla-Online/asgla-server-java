package com.asgla.network;

import com.asgla.Main;
import com.asgla.network.connections.ConnectionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyServer nettyServer;

    public NettyChannelInitializer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
            .addLast("traffic", Main.globalTrafficShapingHandler())
            .addLast("codec-http", new HttpServerCodec())
            .addLast("aggregator", new HttpObjectAggregator(65536))
            .addLast("handler", new ConnectionHandler(this.nettyServer));
    }

}
