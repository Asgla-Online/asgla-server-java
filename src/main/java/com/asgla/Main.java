package com.asgla;

import com.asgla.db.Database;
import com.asgla.db.model.character.Character;
import com.asgla.db.model.user.User;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] params) {
        Database.open();

        User user = User.findById(99999);

        log.info("user {}", user.username());

        List<Character> characters = user.getAll(Character.class);

        characters.forEach(character -> {
            log.info("name {}", character.name());
            log.info("username {}", character.user().username());
        });

        Database.close();
    }

}
