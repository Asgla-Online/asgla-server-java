package com.asgla;

import com.asgla.controller.ServerController;
import com.asgla.db.Database;
import com.asgla.db.model.server.Server;
import com.asgla.requests.threadfactory.RequestThreadFactory;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Main {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    private static final Main _instance = new Main();

    private static final GlobalTrafficShapingHandler GLOBAL_TRAFFIC_SHAPING_HANDLER = new GlobalTrafficShapingHandler(Executors.newScheduledThreadPool(1), 1000);

    private static final ServerController SERVER_CONTROLLER = new ServerController();

    public final ExecutorService requestExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new RequestThreadFactory());

    public static void main(String[] params) {
        ConsoleHandler handler = new ConsoleHandler();

        handler.setLevel(Level.ALL);

        Database.open();

        Server server = Server.findById(1);

        log.info(server.name());

        log.info("Setting up socket");
        serverController().setup();

        Database.close();

        log.info("Server has been started");
    }

    public static Main mainStatic() {
        return _instance;
    }

    public static GlobalTrafficShapingHandler globalTrafficShapingHandler() {
        return GLOBAL_TRAFFIC_SHAPING_HANDLER;
    }

    public static ServerController serverController() {
        return SERVER_CONTROLLER;
    }

}
