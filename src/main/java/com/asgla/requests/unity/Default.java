package com.asgla.requests.unity;

import com.asgla.avatar.player.Player;
import com.asgla.requests.IRequest;
import com.asgla.requests.RequestArgs;
import com.asgla.util.RequestCommand;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Default implements IRequest {

    private static final Logger log = LoggerFactory.getLogger(Default.class);

    @Override
    public void onRequest(Player player, RequestArgs args) {
        log.info("Default request called player: {} args: {}", player.character().name(), args);

        player.dispatch(new JSONObject()
            .element("cmd", RequestCommand.Default)
            .element("Message", "Unknown request.")
        );
    }

}
