package me.matt.grandtheftauto.users.listener;

import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerLastLogin implements Listener {

    private GrandTheftAuto plugin;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public PlayerLastLogin(GrandTheftAuto plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // TODO: 20/09/2021 do the update function
        val user = plugin.getDatabaseManager().getUsers().get(event.getPlayer().getName());
        val lastLogin = new Date();
        user.setLastLogin(lastLogin);

        plugin.getDatabaseManager().getUsers().update(user);
    }

}
