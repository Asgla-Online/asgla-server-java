package com.asgla.db.model.server;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;
import org.javalite.activejdbc.annotations.VersionColumn;

@Cached
@VersionColumn("record_version")
@Table("servers")
public class Server extends Model {

    public String name() {
        return getString("name");
    }

    public String ip() {
        return getString("ip");
    }

    public Integer port() {
        return getInteger("port");
    }

    public Integer playersCount() {
        return getInteger("players_count");
    }

    public void playersCountReset() {
        setInteger("players_count", 0);
        saveIt();
    }

    public void decreaseCount() {
        //setInteger("players_count", playersCount() - 1);
        //saveIt();
    }

    public void increaseCount() {
        //setInteger("players_count", playersCount() + 1);
        //saveIt();
    }

}
