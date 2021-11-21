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
        Bukkit.getPluginManager().registerEvents(this, this.plugin = plugin);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        // TODO: 20/09/2021 do the update function
        val data = plugin.getDatabaseManager().getUsers();
        val user = data.get(e.getPlayer().getName());
        if (user == null) return;
        val lastLogin = new Date();
        user.setLastLogin(lastLogin);
        data.update(user);
    }
}
