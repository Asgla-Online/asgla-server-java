package com.asgla;

import com.asgla.db.Database;
import com.asgla.db.model.user.User;
import org.slf4j.LoggerFactory;

public class Main {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] params) {
        Database.open();

        log.info("Test {}", User.findById(9).getId());

        Database.close();
    }

}
