package com.asgla.requests;

import com.asgla.avatar.player.Player;
import com.asgla.db.Database;
import com.asgla.db.annotations.DatabaseOpen;
import com.asgla.db.annotations.DatabaseTransaction;
import org.javalite.activejdbc.StaleModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record RequestWorker(IRequest request, Player player, RequestArgs args) implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RequestWorker.class);

    @Override
    public void run() {
        boolean hasDatabaseOpen = hasDatabaseOpen();
        boolean hasDatabaseTransaction = hasDatabaseTransaction();

        try {
            if (hasDatabaseOpen) {
                Database.open();
            }

            if (hasDatabaseTransaction) {
                Database.openTransaction();
            }

            request.onRequest(player, args);

            if (hasDatabaseTransaction) {
                Database.commitTransaction();
            }
        } catch (StaleModelException ex) {
            log.error("error in transaction", ex);

            if (hasDatabaseTransaction) {
                Database.rollbackTransaction();
            }
        } catch (Exception ex) {
            log.error("error in handling request", ex);
        } finally {
            Database.close();
        }
    }

    private boolean hasDatabaseOpen() {
        Class<?> meta = request.getClass();
        return meta.isAnnotationPresent(DatabaseOpen.class);
    }

    private boolean hasDatabaseTransaction() {
        Class<?> meta = request.getClass();
        return meta.isAnnotationPresent(DatabaseTransaction.class);
    }

}