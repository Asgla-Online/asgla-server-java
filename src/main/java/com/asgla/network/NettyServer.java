package com.asgla.network;

import com.asgla.api.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


public class NettyServer extends ServerHandler {

    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    private static final int BUFFER_SIZE = 5120;

    private final DefaultChannelGroup channels;
    private final ServerBootstrap bootstrap;

    public NettyServer(String ip, Integer port) {
        super(ip, port);
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.bootstrap = new ServerBootstrap();
    }

    @Override
    public void createSocket() {
        try {
            int threads = Runtime.getRuntime().availableProcessors();

            EventLoopGroup bossGroup = (Epoll.isAvailable() ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads));
            EventLoopGroup workerGroup = (Epoll.isAvailable() ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads));

            this.bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyChannelInitializer(this))
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_RCVBUF, BUFFER_SIZE)
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(BUFFER_SIZE))
                .childOption(ChannelOption.ALLOCATOR, new UnpooledByteBufAllocator(false))
                .childOption(ChannelOption.SO_REUSEADDR, true);
        } catch (Exception exception) {
            log.error("Error creating socket {}", exception.getMessage());
        }
    }

    @Override
    public void bind() {
        this.bootstrap.bind(new InetSocketAddress(this.getIp(), this.getPort())).addListener(objectFuture -> {
            if (!objectFuture.isSuccess()) {
                log.error("Failed to start server on address: {}:{}", this.getIp(), this.getPort());
            } else {
                log.info("Ready for connections!");
            }
        });
    }

    /**
     * Get default channel group of channels
     * @return channels
     */
    public DefaultChannelGroup getChannels() {
        return channels;
    }

}
