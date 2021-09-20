package me.matt.grandtheftauto.database.datasource.impl;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.database.datasource.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL implements DataSource {

    private GrandTheftAuto plugin;

    private Connection connection;

    public MySQL(GrandTheftAuto plugin) {
        this.plugin = plugin;

        val section = plugin.getConfig().getConfigurationSection("Database");

        val username = section.getString("Username");
        val host = section.getString("Host");
        val port = section.getInt("Port");
        val database = section.getString("Database");
        val password = section.getString("Password");

        val url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public void closeConnection() {

    }

}
