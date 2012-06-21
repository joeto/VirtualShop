package org.blockface.virtualshop.persistance;

import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MySQLDB implements Database
{
    private Connection db;

    public void Load() throws Exception
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (final ClassNotFoundException e1) {
            throw new Exception("What on earth are you doing. This isn't CraftBukkit");
        }
        db=DriverManager.getConnection("jdbc:mysql://" + ConfigManager.MySQLHost() + ":" + ConfigManager.getPort() + "/" + ConfigManager.MySQLdatabase() + "?autoReconnect=true&user=" + ConfigManager.MySQLUserName() + "&password=" + ConfigManager.MySQLPassword());
        Chatty.LogInfo("Successfully connected to MySQL Database");
        CheckTables();
    }

    public ResultSet Query(String query) {
        try {
            return db.prepareStatement(query).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void CheckTables() throws Exception
    {
        String query = "create table if not exists stock(`id` integer primary key auto_increment,`damage` integer,`seller` varchar(80) not null,`item` integer not null, `price` float not null,`amount` integer not null)";
        this.Query(query);
        String query2 = "create table transactions(`id` integer primary key auto_increment,`damage` integer not null, `buyer` varchar(20) not null,`seller` varchar(20) not null,`item` integer not null, `cost` float not null,`amount` integer not null)";
        this.Query(query2);
    }


}
