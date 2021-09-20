package me.matt.grandtheftauto.users.listener;

import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.users.model.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private GrandTheftAuto plugin;

    public PlayerJoin(GrandTheftAuto plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getDatabaseManager().getUsers().hasBy(event.getPlayer().getName())) return;

        User user = new User(event.getPlayer().getName());
        plugin.getDatabaseManager().getUsers().add(user);
    }

}
