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
        Bukkit.getPluginManager().registerEvents(this, this.plugin = plugin);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        val p = e.getPlayer();
        val data = plugin.getDatabaseManager().getUsers();
        val name = p.getName();
        if (data.hasBy(name)) return;
        data.add(new User(name));
    }
}
