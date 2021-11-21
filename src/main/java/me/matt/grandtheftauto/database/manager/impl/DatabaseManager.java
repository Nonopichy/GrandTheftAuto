package me.matt.grandtheftauto.database.manager.impl;

import lombok.Getter;
import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.database.datasource.DataSource;
import me.matt.grandtheftauto.database.datasource.impl.MySQL;
import me.matt.grandtheftauto.database.datasource.impl.SQLite;
import me.matt.grandtheftauto.database.manager.ManagerService;
import me.matt.grandtheftauto.database.table.impl.Jobs;
import me.matt.grandtheftauto.database.table.impl.Organizations;
import me.matt.grandtheftauto.database.table.impl.Users;
import org.bukkit.Bukkit;

@Getter
public class DatabaseManager implements ManagerService {

    private GrandTheftAuto plugin;

    private DataSource dataSource;
    private Users users;
    private Jobs jobs;
    private Organizations organizations;

    public DatabaseManager(GrandTheftAuto plugin) {
        this.plugin = plugin;
        val section = plugin.getConfig().getConfigurationSection("Database");
        // optimize this
        val mode = section.getString("Mode");

        switch(mode.toLowerCase()) {
            case "mysql":
                dataSource = new MySQL(plugin);
                break;
            case "sqlite":
                dataSource = new SQLite(plugin);
                break;
            default:
                Bukkit.getConsoleSender().sendMessage("Modo errado. Reveja a config.yml"); // use log
                break;
        }

    }

    @Override
    public void load() {
        users = new Users(plugin);
        jobs = new Jobs(plugin);
        organizations = new Organizations(plugin);

        users.createTable();
        jobs.createTable();
        organizations.createTable();
    }

    @Override
    public void reload() {
        // Not used.
    }

    @Override
    public void unload() {
        dataSource.closeConnection();
    }

}
