package me.matt.grandtheftauto.users.listener;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

public class PlayerLastLogin implements Listener {

    private final GrandTheftAuto plugin;

    public PlayerLastLogin(GrandTheftAuto plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // TODO: 20/09/2021 do the update function
        val user = plugin.getDatabaseManager().getUsers().get(event.getPlayer().getName());
        if (user == null) return;
        val lastLogin = new Date();
        user.setLastLogin(lastLogin);

        plugin.getDatabaseManager().getUsers().update(user);
    }

}
