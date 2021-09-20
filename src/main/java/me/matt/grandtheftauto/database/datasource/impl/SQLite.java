package me.matt.grandtheftauto.database.datasource.impl;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.database.datasource.DataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite implements DataSource {

    private GrandTheftAuto plugin;

    private String url;

    public SQLite(GrandTheftAuto plugin) {
        this.plugin = plugin;

        val file = new File(plugin.getDataFolder(), "database.db");
        this.url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public void closeConnection() {
        // Not used.
    }

}
