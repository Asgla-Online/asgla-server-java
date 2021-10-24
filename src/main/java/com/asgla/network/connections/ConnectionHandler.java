package com.asgla.network.connections;

import com.asgla.Main;
import com.asgla.avatar.player.Player;
import com.asgla.db.Database;
import com.asgla.db.model.character.Character;
import com.asgla.network.NettyPlayerNetwork;
import com.asgla.network.NettyServer;
import com.asgla.network.session.User;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.requests.RequestFactory;
import com.asgla.requests.RequestWorker;
import com.asgla.util.RequestCommand;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static com.asgla.Main.serverController;
import static com.asgla.controller.UserController.userManager;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ConnectionHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    private WebSocketServerHandshaker webSocketServerHandshaker;
    private final NettyServer server;

    public ConnectionHandler(NettyServer server) {
        super();
        this.server = server;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        server.getChannels().remove(channelHandlerContext.channel());

        User user = channelHandlerContext.channel().attr(User.PLAYER_KEY).get();

        user.disconnect();

        log.info("[{}] Disconnecting ip {} ", user.network().getConnectionId(), channelHandlerContext.channel().remoteAddress().toString().replace("/", "").split(":")[0]);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        User user = new User(new NettyPlayerNetwork(channelHandlerContext.channel(), channelHandlerContext.channel().hashCode()));

        channelHandlerContext.channel().attr(User.PLAYER_KEY).set(user);

        if (!server.getChannels().add(channelHandlerContext.channel())) {
            channelHandlerContext.disconnect();
            return;
        }

        log.info("[{}] Connection from {} ", user.network().getConnectionId(), channelHandlerContext.channel().remoteAddress().toString().replace("/", "").split(":")[0]);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) throws Exception {
        log.error("exceptionCaught {}", throwable.getMessage());
        throwable.printStackTrace();
        channelhandlercontext.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof FullHttpRequest) {
                handleHttpRequest(ctx, (FullHttpRequest) msg);
            } else if (msg instanceof WebSocketFrame) {
                handleWebSocketFrame(ctx, (WebSocketFrame) msg);
            }
        } catch (Exception e) {
            ctx.channel().close();
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame closeWebSocketFrame) {
            webSocketServerHandshaker.close(channelHandlerContext.channel(), closeWebSocketFrame.retain());
            return;
        } else if (frame instanceof PingWebSocketFrame) {
            channelHandlerContext.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        } else if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException("%s frame types not supported".formatted(frame.getClass().getName()));
        }

        log.info("Received > {}", ((TextWebSocketFrame) frame).text());

        try {
            User user = channelHandlerContext.channel().attr(User.PLAYER_KEY).get();

            if (user != null) {
                JSONObject json = JSONObject.fromObject(((TextWebSocketFrame) frame).text());

                String cmd = json.getString("Cmd");

                JSONArray jsonArray= json.getJSONArray("Params");

                String[] params = new String[jsonArray.size()];

                //TODO: Improve
                int i = 0;
                for (Object x : jsonArray) {
                    params[i] = String.valueOf(x);
                    i++;
                }

                log.info(user.player() == null ? "player is null" : "player ok");

                //noinspection SwitchStatementWithTooFewBranches
                switch (cmd) {
                    case "Login" -> Login(user, params);
                    default -> {
                        IRequest request = RequestFactory.REQUESTS.create(cmd);
                        Main.mainStatic().requestExecutor.execute(new RequestWorker(request, user.player(), RequestArgs.parse(params)));
                    }
                }
            }
        } catch (WebSocketHandshakeException webSocketHandshakeException) {
            log.error("WebSocketHandshakeException {}", webSocketHandshakeException.getMessage());
            channelHandlerContext.channel().close();
        }/* catch (RuntimeException runtimeException) {
            log.error("RuntimeException {}", runtimeException.getMessage());
            channelHandlerContext.channel().close();
        }*/
    }

    public void Login(User user, String[] params) {
        Database.open();

        JSONObject jsonObject = new JSONObject()
            .element("cmd", RequestCommand.Login);

        Character character = Character.findById(1);//Character.findFirst("token = ?", params[0]);

        if (character.exists()) {
            if (userManager().getUserByName(character.name()) == null) {
                user.name(character.name().toLowerCase(Locale.US));

                userManager().addUser(user);

                Player player = new Player(user, (long) character.getId());

                player.dispatch(jsonObject
                    .element("status", true)
                    .element("message", "Success.")
                );

                serverController().server().increaseCount();
            } else {
                user.network().send(jsonObject
                    .element("status", false)
                    .element("message", "Already logged.")
                );

                removeConnection(character.name());
            }
        } else {
            user.network().send(jsonObject
                .element("status", false)
                .element("message", "Character information can not be retrieved.")
            );
        }

        Database.close();
    }

    private void removeConnection(String name) {
        User user = userManager().getUserByName(name);

        if (user != null) {
            user.network().close();
            userManager().removeUser(user);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        if (request.method() != GET) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        //allow only / uri
        if (!request.uri().equals("/")) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), null, false);

        webSocketServerHandshaker = factory.newHandshaker(request);

        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            return;
        }

        User user = ctx.channel().attr(User.PLAYER_KEY).get();

        //Cloudflare request.headers().get("CF-Connecting-IP")
        //user.ip(request.headers().get("CF-Connecting-IP"));

        webSocketServerHandshaker.handshake(ctx.channel(), request);
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse res) {
        int code = res.status().code();

        if (code != 200) {
            String errorMessage = "<h1>" + res.status().reasonPhrase() + " "  + code + "</h1>";
            ByteBuf buf = Unpooled.copiedBuffer(errorMessage, CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);

        if (!HttpUtil.isKeepAlive(request) || res.status().code() != 200) {
            log.info("ConnectionHandler CLOSE isKeepAlive getStatus");
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(FullHttpRequest req) {
        return "ws://" + req.headers().get(HttpHeaderNames.HOST);
    }

}
